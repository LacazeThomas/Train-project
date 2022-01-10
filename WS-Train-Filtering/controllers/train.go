package controllers

import (
	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"gorm.io/gorm/clause"
	"main/dataGenerator"
	"main/models"
	"net/http"
	"strconv"
)

type TicketInput struct {
	Flexible bool            `json:"flexible"`
	Type     models.SeatType `json:"type"`
	UserID   string          `json:"userID"`
}

type RoundTripTrains struct {
	TrainsOutbound []models.Train `json:"trainsoutbound"`
	TrainsReturn   []models.Train `json:"trainsreturn"`
}

// @Summary      Show all trains
// @Description  get trains with filters
// @Router       /trains [get]
// @Accept       json
// @Produce      json
// @Success      200  {object}  RoundTripTrains
// @Param        departure_station   query      string  false  "Departure station"
// @Param        arrival_station   query      string  false  "Arrival station"
// @Param        return_arrival_date   query      string  false  "Return arrival date"
// @Param        return_departure_date   query      string  false  "Return departure date"
// @Param        outbound_arrival_date   query      string  false  "Outbound arrival date"
// @Param        outbound_departure_date   query      string  false  "Outbound departure"
// @Param        number_ticket_first   query      int  false  "Number ticket first"
// @Param        number_ticket_business   query      int  false  "Number ticket business"
// @Param        number_ticket_business   query      int  false  "Number ticket business"
func (DB *Database) GetTrainsFilter(c *gin.Context) {
	var trains RoundTripTrains

	clausesOutbound := make([]clause.Expression, 0)
	clausesReturn := make([]clause.Expression, 0)

	if value := c.Query("departure_station"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Eq{Column: "departure_station", Value: value})
		clausesReturn = append(clausesReturn, clause.Eq{Column: "arrival_station", Value: value})
	}

	if value := c.Query("arrival_station"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Eq{Column: "arrival_station", Value: value})
		clausesReturn = append(clausesReturn, clause.Eq{Column: "departure_station", Value: value})
	}

	if value := c.Query("return_arrival_date"); value != "" {
		clausesReturn = append(clausesReturn, clause.Like{Column: "arrival_date", Value: value + "%"})
	}

	if value := c.Query("return_departure_date"); value != "" {
		clausesReturn = append(clausesReturn, clause.Like{Column: "departure_date", Value: value + "%"})
	}

	if value := c.Query("outbound_arrival_date"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Like{Column: "arrival_date", Value: value + "%"})
	}

	if value := c.Query("outbound_departure_date"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Like{Column: "departure_date", Value: value + "%"})
	}

	if value := c.Query("number_ticket_first"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Gte{Column: "number_ticket_first_available", Value: value})
		clausesReturn = append(clausesReturn, clause.Gte{Column: "number_ticket_first_available", Value: value})
	}

	if value := c.Query("number_ticket_standard"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Gte{Column: "number_ticket_standard_available", Value: value})
		clausesReturn = append(clausesReturn, clause.Gte{Column: "number_ticket_standard_available", Value: value})
	}

	if value := c.Query("number_ticket_business"); value != "" {
		clausesOutbound = append(clausesOutbound, clause.Gte{Column: "number_ticket_business_available", Value: value})
		clausesReturn = append(clausesReturn, clause.Gte{Column: "number_ticket_business_available", Value: value})
	}

	//Do not display train with no available place
	clausesReturn = append(clausesReturn,
		clause.Or(
			clause.Gt{Column: "number_ticket_first_available", Value: "0"},
			clause.Gt{Column: "number_ticket_standard_available", Value: "0"},
			clause.Gt{Column: "number_ticket_business_available", Value: "0"},
		),
	)

	clausesOutbound = append(clausesOutbound,
		clause.Or(
			clause.Gt{Column: "number_ticket_first_available", Value: "0"},
			clause.Gt{Column: "number_ticket_standard_available", Value: "0"},
			clause.Gt{Column: "number_ticket_business_available", Value: "0"},
		),
	)

	DB.Clauses(clausesOutbound...).Find(&trains.TrainsOutbound)
	DB.Clauses(clausesReturn...).Find(&trains.TrainsReturn)

	c.JSON(http.StatusOK, trains)
}

// @Summary      Show a train
// @Description  get train with id
// @Router       /trains/{id} [get]
// @Accept       json
// @Param        id   path      string  true  "Train ID"
// @Produce      json
// @Success      200  {object}  models.Train
func (DB *Database) FindTrainByID(c *gin.Context) {
	// Get model if exist
	var train models.Train
	if err := DB.Where("id = ?", c.Param("id")).First(&train).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Record not found!"})
		return
	}

	c.JSON(http.StatusOK, train)
}

// @Summary      Reserve train
// @Description  get ticket by trainID
// @Router       /trains/{id}/reserve [post]
// @Param default body TicketInput true "Type can be Standard=0 Business=1 First=2"
// @Param        id   path      string  true  "Train ID"
// @Accept       json
// @Produce      json
func (DB *Database) PostTicket(c *gin.Context) {
	// Validate input
	var input TicketInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, err.Error())
		return
	}

	var train models.Train
	if err := DB.Where("id = ?", c.Param("id")).First(&train).Error; err != nil {
		c.JSON(http.StatusOK, false)
		return
	}

	//Checking seat available
	switch models.SeatType(input.Type) {
	case models.Standard:
		if train.NumberTicketStandardAvailable > 0 {
			train.NumberTicketStandardAvailable--
		} else {
			c.JSON(http.StatusOK, "No available place")
			return
		}

		break
	case models.First:
		if train.NumberTicketFirstAvailable > 0 {
			train.NumberTicketFirstAvailable--
		} else {
			c.JSON(http.StatusOK, "No available place")
			return
		}
		break
	case models.Business:
		if train.NumberTicketBusinessAvailable > 0 {
			train.NumberTicketBusinessAvailable--
		} else {
			c.JSON(http.StatusOK, "No available place")
			return
		}
		break
	default:
		c.JSON(http.StatusOK, "Wrong class type")
		return
	}

	DB.Save(train)

	var ticket models.Ticket
	ticket.Id = uuid.New()
	ticket.Type = models.SeatType(input.Type)
	ticket.Flexible = input.Flexible
	ticket.TrainID = train.Id
	ticket.UserID = input.UserID

	DB.Save(ticket)

	c.JSON(http.StatusOK, true)
}

// @Summary      Get tickets by trainID
// @Description  Get all tickets associated with train
// @Param        id   path      string  true  "Train ID"
// @Router       /trains/{id}/tickets [get]
// @Accept       json
// @Produce      json
// @Success      200  {object}  models.Ticket
func (DB *Database) FindTicketsByTrainID(c *gin.Context) {
	var tickets []models.Ticket

	if err := DB.Where("train_id = ?", c.Param("id")).Find(&tickets).Error; err != nil {
		c.JSON(http.StatusNotFound, "Record not found!")
		return
	}

	c.JSON(http.StatusOK, tickets)
}

// @Summary      Get tickets by userID
// @Description  Get all tickets associated with user
// @Param        id   path      string  true  "User ID"
// @Router       /users/{id}/tickets [get]
// @Accept       json
// @Produce      json
// @Success      200  {object}  models.Ticket
func (DB *Database) FindTicketsByUserID(c *gin.Context) {
	var tickets []models.Ticket

	if err := DB.Where("user_id = ?", c.Param("id")).Find(&tickets).Error; err != nil {
		c.JSON(http.StatusNotFound, "Record not found!")
		return
	}

	c.JSON(http.StatusOK, tickets)
}

// @Summary      Delete tickets by ticketID
// @Param        id   path      string  true  "TicketID ID"
// @Router       /tickets/{id} [delete]
// @Param        userID   query      string  false  "userID"
// @Accept       json
// @Produce      json
func (DB *Database) DeleteTicket(c *gin.Context) {
	var ticket models.Ticket
	if err := DB.Where("id = ?", c.Param("id")).First(&ticket).Error; err != nil {
		c.JSON(http.StatusNotFound, "Record not found!")
		return
	}

	if ticket.UserID != c.Query("userID") {
		c.JSON(http.StatusOK, "Not authorised")
		return
	}

	var train models.Train
	if err := DB.Where("id = ?", ticket.TrainID).First(&train).Error; err != nil {
		c.JSON(http.StatusNotFound, "Record not found!")
		return
	}

	switch models.SeatType(ticket.Type) {
	case models.Standard:
		train.NumberTicketStandardAvailable++
		break
	case models.First:
		train.NumberTicketFirstAvailable++
		break
	case models.Business:
		train.NumberTicketBusinessAvailable++
		break
	default:
		c.JSON(http.StatusBadRequest, "Unknown error!")
		return
	}

	DB.Save(&train)
	DB.Delete(&ticket)

	c.JSON(http.StatusOK, true)
}

// @Summary      Generate random trains
// @Param        number_generate_trains   query      string  true  "Number of trains to generate"
// @Router       /trains/generator [post]
// @Accept       json
// @Produce      json
func (DB *Database) GenerateTrain(c *gin.Context) {

	value := c.Query("number_generate_trains")
	num, err := strconv.Atoi(value)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "number_generate_trains need to be a int"})
	}

	trains := dataGenerator.GenerateTrain(num)
	DB.Save(trains)
}
