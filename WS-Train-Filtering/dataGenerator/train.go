package dataGenerator

import (
	"github.com/brianvoe/gofakeit/v6"
	"github.com/google/uuid"
	"main/models"
	"time"
)

func generateRandomTrain() (t models.Train) {

	t.Id, _ = uuid.NewUUID()
	t.DepartureDate = gofakeit.DateRange(time.Now(), time.Now().Add(time.Duration(+36000000000000)))
	t.ArrivalDate = gofakeit.DateRange(t.DepartureDate, t.DepartureDate.AddDate(0, 0, 1))
	t.ArrivalStation = gofakeit.City()
	t.DepartureStation = gofakeit.City()
	t.Name = gofakeit.Company()
	t.NumberTicketBusinessAvailable = 1
	t.NumberTicketFirstAvailable = 1
	t.NumberTicketStandardAvailable = 1
	t.NumberSeatStandard = gofakeit.Number(1, 10)
	t.NumberSeatFirst = gofakeit.Number(1, 10)
	t.NumberSeatBusiness = gofakeit.Number(1, 10)

	return

}

func GenerateTrain(maxTrain int) (train []models.Train) {
	for i := 0; i < maxTrain; i++ {
		train = append(train, generateRandomTrain())
	}

	//Create a roundtrip, better for testing
	train[0].DepartureStation = "Paris"
	train[0].ArrivalStation = "Marseille"
	train[0].NumberTicketBusinessAvailable = 10
	train[0].NumberTicketFirstAvailable = 10
	train[0].NumberTicketStandardAvailable = 10
	train[0].NumberSeatStandard = 10
	train[0].NumberSeatFirst = 10
	train[0].NumberSeatBusiness = 10

	train[1].DepartureStation = "Marseille"
	train[1].ArrivalStation = "Paris"
	train[1].NumberTicketBusinessAvailable = 10
	train[1].NumberTicketFirstAvailable = 10
	train[1].NumberTicketStandardAvailable = 10
	train[1].NumberSeatStandard = 10
	train[1].NumberSeatFirst = 10
	train[1].NumberSeatBusiness = 10

	return
}
