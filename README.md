
# Order Management Service

OMS is an service that helps people add products, vendor details selling those products, getting lowest price for a product, placing an order for a particular product to a particular vendor

## APIs

1. Create a product

```
sample request:
url: POST /products
body: 
{
    "name": "IPHONE",
    "details": {
        "make": "Apple",
        "memory": "16GB",
        "storage": "128GB",
        "color": "White"
    },
    "categoryUuid":"42af2f40-37c3-4775-be7a-b6c3cb6cb075",
    "basePrice": 35000
}

sample response:
status: 201 
body:
{
    "uuid": "9d3c0171-f455-437f-b988-ecf9205d03b8",
    "categoryUuid": "42af2f40-37c3-4775-be7a-b6c3cb6cb075",
    "createdAt": "2021-03-07T10:56:57.116651",
    "updatedAt": "2021-03-07T10:56:57.116764",
    "name": "IPHONE",
    "basePrice": 35000.0,
    "details": {
        "make": "Apple",
        "memory": "16GB",
        "storage": "128GB",
        "color": "White"
    },
    "status": "ACTIVE",
    "vendorProductRelations": null
}
```
2. Add price for a product and corresponding vendor details

```
sample request:
url: POST /vendor-product-relations
body: 
{
    "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
    "vendorPrice": 37000.0,
    "tax": "FIVE",
    "relationStatus": "AVAILABLE",
    "availableQuantity": 10,
    "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
    "productUuid": "66468cf6-4e43-455d-aba6-e898c35f5417"
}
sample response:
status: 201
body:
{
    "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
    "createdAt": "2021-03-07T11:26:23.473402",
    "updatedAt": "2021-03-07T11:26:23.47343",
    "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
    "vendorPrice": 37000.0,
    "taxSlab": "FIVE",
    "availableQuantity": 10.0,
    "status": "AVAILABLE",
    "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
    "vendorName": "marvin"
}
```

3. Update details for a product and vendor details

```
url: PATCH /vendor-product-relations/uuid
sample request body: 
{
    "availableQuantity": 100,
    "tax": "TWELVE",
    "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
    "vendorPrice": 37000
}

sameple response:
status: 200
body:
{
    "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
    "createdAt": "2021-03-07T11:26:23.473402",
    "updatedAt": "2021-03-07T11:26:23.47343",
    "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
    "vendorPrice": 37000.0,
    "taxSlab": "TWELVE",
    "availableQuantity": 100.0,
    "status": "AVAILABLE",
    "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
    "vendorName": null
}
```

4. Fetch price quote for a particular product

```
url: GET /products/uuid/price-quote
sample response: 
status: 200
body: 
{
    "priceQuote": {
        "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
        "createdAt": "2021-03-07T11:26:23.473402",
        "updatedAt": "2021-03-07T11:27:45.052916",
        "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
        "vendorPrice": 37000.0,
        "taxSlab": "TWELVE",
        "availableQuantity": 100.0,
        "status": "AVAILABLE",
        "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
        "vendorName": "marvin"
    },
    "product": {
        "uuid": "66468cf6-4e43-455d-aba6-e898c35f5417",
        "categoryUuid": "42af2f40-37c3-4775-be7a-b6c3cb6cb075",
        "createdAt": "2021-03-07T11:26:07.34877",
        "updatedAt": "2021-03-07T11:26:07.348848",
        "name": "IPHONE",
        "basePrice": 35000.0,
        "details": {
            "make": "Apple",
            "color": "White",
            "memory": "16GB",
            "storage": "128GB"
        },
        "status": "ACTIVE",
        "vendorProductRelations": [
            {
                "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
                "createdAt": "2021-03-07T11:26:23.473402",
                "updatedAt": "2021-03-07T11:27:45.052916",
                "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
                "vendorPrice": 37000.0,
                "taxSlab": "TWELVE",
                "availableQuantity": 100.0,
                "status": "AVAILABLE",
                "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
                "vendorName": "marvin"
            }
        ]
    }
}
```
5. Get details of a product

```
url: GET /products/uuid
sample response: 
status: 200
body:
{
    "uuid": "66468cf6-4e43-455d-aba6-e898c35f5417",
    "categoryUuid": "42af2f40-37c3-4775-be7a-b6c3cb6cb075",
    "createdAt": "2021-03-07T11:26:07.34877",
    "updatedAt": "2021-03-07T11:26:07.348848",
    "name": "IPHONE",
    "basePrice": 35000.0,
    "details": {
        "make": "Apple",
        "color": "White",
        "memory": "16GB",
        "storage": "128GB"
    },
    "status": "ACTIVE",
    "vendorProductRelations": [
        {
            "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
            "createdAt": "2021-03-07T11:26:23.473402",
            "updatedAt": "2021-03-07T11:27:45.052916",
            "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
            "vendorPrice": 37000.0,
            "taxSlab": "TWELVE",
            "availableQuantity": 100.0,
            "status": "AVAILABLE",
            "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
            "vendorName": null
        }
    ]
}
```

6. Create Order for a particular product and vendor

```
url: POST /orders
sample request body: 
{
    "orderParams": {
        "deliveryAddressUuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae3",
        "deliveryDate": "31/03/2021",
        "buyerUuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae4"
    },
    "orderItemParams": [{
        "productUuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a5f",
        "quantity": 100,
        "vendorUuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a4f",
        "originAddressUuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a3a" 
    }]
}
sample response:
status: 201
body:
{
    "uuid": "208efdb2-7e4c-44e3-b48b-9d104efa5b5f",
    "createdAt": "2021-03-07T11:35:26.879834",
    "updatedAt": "2021-03-07T11:35:26.879863",
    "deliveryAddressUuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae3",
    "status": "ORDERED",
    "buyerUuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae4",
    "totalAmount": 3330000.0,
    "deliveryDate": "2021-03-31T00:00:00.000+00:00",
    "orderItems": [
        {
            "uuid": "b3c3f3ae-b6cd-4773-835e-ce580bf5b8d9",
            "createdAt": "2021-03-07T11:35:26.888682",
            "updatedAt": "2021-03-07T11:35:26.888966",
            "product": {
                "uuid": "66468cf6-4e43-455d-aba6-e898c35f5417",
                "categoryUuid": "42af2f40-37c3-4775-be7a-b6c3cb6cb075",
                "createdAt": "2021-03-07T11:26:07.34877",
                "updatedAt": "2021-03-07T11:26:07.348848",
                "name": "IPHONE",
                "basePrice": 35000.0,
                "details": {
                    "make": "Apple",
                    "color": "White",
                    "memory": "16GB",
                    "storage": "128GB"
                },
                "status": "ACTIVE",
                "vendorProductRelations": [
                    {
                        "uuid": "f1484ce9-ce1c-4d32-855d-aa2c520f56ec",
                        "createdAt": "2021-03-07T11:30:54.363971",
                        "updatedAt": "2021-03-07T11:31:26.969918",
                        "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
                        "vendorPrice": 37000.0,
                        "taxSlab": "FIVE",
                        "availableQuantity": 0.0,
                        "status": "OUT_OF_STOCK",
                        "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
                        "vendorName": null
                    },
                    {
                        "uuid": "7e7bb0ca-2e85-4c9a-b907-3b6efaab088d",
                        "createdAt": "2021-03-07T11:26:23.473402",
                        "updatedAt": "2021-03-07T11:35:26.893534",
                        "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
                        "vendorPrice": 37000.0,
                        "taxSlab": "TWELVE",
                        "availableQuantity": 0.0,
                        "status": "OUT_OF_STOCK",
                        "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
                        "vendorName": null
                    }
                ]
            },
            "quantity": 90.0,
            "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
            "price": 37000.0,
            "status": "ORDERED",
            "taxSlab": "TWELVE",
            "originAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
        }
    ]
}
```

