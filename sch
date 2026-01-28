openapi: 3.0.3

info:
  title: Transaction Analytics Service
  version: 1.0.0
  description: Microservice to generate transaction analytics for merchant console

servers:
  - url: https://titanms.aws.nttdatapay.com/v1
    description: Production
  - url: http://titanmspreprod.aws.nttdatapay.com/v1
    description: Preprod Development
  - url: http://titanms.awsuat.nttdatapay.com/v1
    description: UAT Development
  - url: http://projectd.atomtech.in/v1
    description: Pre-Uat Development
  - url: http://localhost:8080/v1
    description: Local Development

tags:
  - name: transaction-analytics
    description: Transaction analytics operations

paths:
  /fetchTxnAnalyticsData:
    parameters:
      - $ref: '#/components/parameters/TraceIdHeader'
      - $ref: '#/components/parameters/ReceivedAtHeader'
    post:
      tags: [transaction-analytics]
      summary: Fetch Transaction summary
      operationId: fetchTransactionSummary
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/TxnAnalyticPayload'
      responses:
        '200':
          description: Successfully retrieved transaction summary
          headers:
            X-Trace-Id:
              $ref: '#/components/headers/TraceIdResponseHeader'
            X-Received-At:
              $ref: '#/components/headers/ReceivedAtResponseHeader'
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TxnAnalyticResponse'
        '400':
          $ref: '#/components/responses/BadRequest'
        '500':
          $ref: '#/components/responses/InternalServerError'
        '504':
          $ref: '#/components/responses/GatewayTimeout'

components:
  # Headers
  headers:

    # Request Headers
    TraceIdHeader:
      description: Unique Trace ID for request tracking
      schema:
        type: string
        format: uuid

    ReceivedAtHeader:
      description: Timestamp when the request was received
      schema:
        type: string
        format: date-time

    # Response Headers
    TraceIdResponseHeader:
      description: Unique Trace ID returned in the response
      schema:
        type: string
        format: uuid

    ReceivedAtResponseHeader:
      description: Timestamp when the response was generated
      schema:
        type: string
        format: date-time

  # Parameters
  parameters:
    TraceIdHeader:
      name: X-Trace-Id
      in: header
      required: true
      description: Unique Trace ID for request tracking
      schema:
        type: string
        format: uuid

    ReceivedAtHeader:
      name: X-Received-At
      in: header
      required: true
      description: Timestamp when the request was received
      schema:
        type: string
        format: date-time
        example: "2025-11-24 12:35:56"

  # Responses
  responses:

    BadRequest:
      description: Bad Request
      headers:
        X-Trace-Id:
          $ref: '#/components/headers/TraceIdResponseHeader'
        X-Received-At:
          $ref: '#/components/headers/ReceivedAtResponseHeader'
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/ErrorResponse'

    InternalServerError:
      description: Internal Server Error
      headers:
        X-Trace-Id:
          $ref: '#/components/headers/TraceIdResponseHeader'
        X-Received-At:
          $ref: '#/components/headers/ReceivedAtResponseHeader'
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/ErrorResponse'

    GatewayTimeout:
      description: Gateway Timeout
      headers:
        X-Trace-Id:
          $ref: '#/components/headers/TraceIdResponseHeader'
        X-Received-At:
          $ref: '#/components/headers/ReceivedAtResponseHeader'
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/ErrorResponse'

  # Schemas
  schemas:

    TxnAnalyticPayload:
      type: object
      required: [txnAnalyticRequest, commonBean]
      properties:
        txnAnalyticRequest:
          $ref: '#/components/schemas/TxnAnalyticRequest'
        commonBean:
          $ref: '#/components/schemas/CommonBean'

    TxnAnalyticRequest:
      type: object
      required: [merchId, userRole, requestorId, fromDate, toDate, channelId, channelName]
      properties:
        merchId: # There is no validation for this field because its value is not restricted by the business rules.
          type: integer
          format: int32
          example: 7
        requestorId: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          maxLength: 30
          example: "M1285789568568"
        userId: # There is no validation for this field because its value is not restricted by the business rules.
          type: integer
          format: int32
          example: 7
        userRole: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          enum: [MERCHANT, RESELLER]
          example: MERCHANT
        fromDate: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          format: date
          example: "2026-01-01"
        toDate: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          format: date
          example: "2026-01-07"
        channelId: # There is no validation for this field because its value is not restricted by the business rules.
          type: integer
          format: int32
          example: 1
        channelName: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          enum: [Online, POS, QR, IVR]
          example: "Online"

    CommonBean:
      type: object
      description: Used to fetch merchant id.
      properties:
        sourceId: # There is no validation for this field because its value is not restricted by the business rules.
          type: integer
          format: int32
          example: 10
        subSourceId: # There is no validation for this field because its value is not restricted by the business rules.
          type: integer
          format: int32
          example: 45
        userRole: # There is no validation for this field because its value is not restricted by the business rules.
          type: string
          enum: [MERCHANT, RESELLER]
          example: MERCHANT

    TxnAnalyticResponse:
      type: object
      properties:
        merchId:
          type: integer
          example: 7
        requestorId:
          type: string
          example: "M1285789568568"
        successCount:
          type: integer
          example: 50
        failureCount:
          type: integer
          example: 50
        initiatedCount:
          type: integer
          example: 100
        revertedCount:
          type: integer
          example: 70
        awaitedCount:
          type: integer
          example: 30
        dayWiseTxnSummary:
          type: array
          items:
            $ref: '#/components/schemas/DayWiseTxnSummary'
        productWiseTxnSummary:
          type: array
          items:
            $ref: '#/components/schemas/ProductWiseTxnSummary'

    DayWiseTxnSummary:
      type: object
      properties:
        date:
          type: string
          format: date
          example: "2026-01-01"
        dayWiseSuccessCount:
          type: integer
          example: 10
        dayWiseFailedCount:
          type: integer
          example: 20

    ProductWiseTxnSummary:
      type: object
      properties:
        productName:
          type: string
          example: "UPI"
        productWiseSuccessCount:
          type: integer
          example: 10

    ErrorResponse:
      type: object
      properties:
        code:
          type: string
        message:
          type: string
