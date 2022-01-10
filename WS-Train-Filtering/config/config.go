package config

import (
	"github.com/caarlos0/env"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"main/controllers"
	"main/models"
)

type AppConfig struct {
	Env string `env:"ENV" envDefault:"dev"`
}

//IsDev return true if application is on dev stack
func (app *AppConfig) IsDev() bool {
	return IsDev(app.Env)
}

//IsDev return true if application is on dev stack
func IsDev(env string) bool {
	return env == "dev" || env == "development"
}

// LoadCfg loads the config file.
func LoadCfg() (AppConfig, error) {

	cfg := AppConfig{}
	err := env.Parse(&cfg)
	if err != nil {
		return AppConfig{}, err
	}

	return cfg, nil
}

func ConnectDatabase() (controllers.Database, error) {
	db, err := gorm.Open(sqlite.Open("rest.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}

	// Migrate the schema
	err = db.AutoMigrate(&models.Ticket{}, &models.Train{})
	if err != nil {
		return controllers.Database{}, err
	}

	return controllers.Database{DB: db}, nil
}
