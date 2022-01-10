FROM golang:alpine3.15 as builder

RUN apk update && apk add git gcc g++
COPY . $GOPATH/src/github.com/WS-Train-Filtering/
WORKDIR $GOPATH/src/github.com/WS-Train-Filtering/
RUN go build -o /go/bin/WS-Train-Filtering

ENV ENVIRONMENT prod

FROM alpine:3.15
EXPOSE 8081
COPY --from=builder /go/bin/WS-Train-Filtering /bin/WS-Train-Filtering

ENTRYPOINT ["/bin/WS-Train-Filtering"]