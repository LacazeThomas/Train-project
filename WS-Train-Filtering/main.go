package main

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/swaggo/files"
	"github.com/swaggo/gin-swagger"
	"go.uber.org/zap"
	"log"
	"main/config"
	_ "main/docs"
	"os"
	"time"
)

func main() {
	var logger *zap.Logger
	gin.SetMode(gin.ReleaseMode)

	// Load configuration
	cfg, err := config.LoadCfg()
	if err != nil {
		log.Printf("Error to parsing env %v", err)
		os.Exit(0)
	}

	// Set log level
	if cfg.IsDev() {
		logger, err = zap.NewDevelopment()
	} else {
		logger, err = zap.NewProduction()
	}

	if err != nil {
		zap.S().Error("Error to initialize logger")
	}

	defer func(logger *zap.Logger) {
		err := logger.Sync()
		if err != nil {
			zap.Error(err)
		}
	}(logger)
	zap.ReplaceGlobals(logger)

	db, err := config.ConnectDatabase()
	if err != nil {
		zap.Error(err)
	}

	r := gin.Default()
	r.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"*"},
		AllowMethods:     []string{"PUT", "PATCH", "GET", "POST"},
		AllowHeaders:     []string{"Origin"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: true,
		AllowOriginFunc: func(origin string) bool {
			return origin == "*"
		},
		MaxAge: 12 * time.Hour,
	}))

	// Routes
	r.GET("/trains", db.GetTrainsFilter)
	r.POST("/trains/:id/reserve", db.PostTicket)
	r.GET("/trains/:id/tickets", db.FindTicketsByTrainID)
	r.GET("/users/:id/tickets", db.FindTicketsByUserID)
	r.DELETE("/tickets/:id", db.DeleteTicket)
	r.GET("/trains/:id", db.FindTrainByID)
	r.POST("/trains/generator", db.GenerateTrain)

	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	err = r.Run(":8081")
	if err != nil {
		log.Println("Unable to start web server")
	}
}
