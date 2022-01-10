package models

import "github.com/google/uuid"

type SeatType int

const (
	Standard SeatType = 0
	Business          = 1
	First             = 2
)

// @description And so forth.
type Ticket struct {
	Id       uuid.UUID `gorm:"primary_key;type:char(36);"`
	TrainID  uuid.UUID
	Train    Train `gorm:"foreignKey:TrainID" json:"-"`
	Flexible bool
	Type     SeatType
	UserID   string
}
