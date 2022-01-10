package models

import (
	"github.com/google/uuid"
	"time"
)

type Train struct {
	Id                            uuid.UUID `gorm:"primary_key;type:char(36);"`
	Name                          string    `json:"name"`
	DepartureStation              string    `json:"departure_station"`
	ArrivalStation                string    `json:"arrival_station"`
	DepartureDate                 time.Time `json:"departure_date"`
	ArrivalDate                   time.Time `json:"arrival_date"`
	NumberSeatFirst               int       `json:"number_seat_first"`
	NumberSeatBusiness            int       `json:"number_seat_business"`
	NumberSeatStandard            int       `json:"number_seat_standard"`
	NumberTicketFirstAvailable    int       `json:"number_ticket_first_available"`
	NumberTicketBusinessAvailable int       `json:"number_ticket_business_available"`
	NumberTicketStandardAvailable int       `json:"number_ticket_standard_available"`
}
