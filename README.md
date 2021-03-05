
# Order Management Service

OMS is an service that helps people add products, vendor details selling those products, getting lowest price for a product, placing an order for a particular product to a particular vendor

## APIs

1. Create a product

```
url: POST /products
sample request body: 
{
    "name": "Iphone XS",
    "details": {
        "make": "Apple",
        "memory": "16GB",
        "storage": "256GB",
        "color": "White"
    },
    "basePrice": 50000,
    "categoryUuid":"54f2f0e7-1b83-43ef-9275-b8f15ce1d764"
}
```
2. Add price for a product and corresponding vendor details

```
url: POST /vendor-product-relations
sample request body: 
{
    "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
    "vendorPrice": 52000.0,
    "availableQuantity": 10,
    "status": "AVAILABLE",
    "vendorName": "Marvin",
    "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8",
    "productUuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a5f"
}
```

3. Fetch price quote for a particular product

```
url: GET /products/uuid/price-quote
sample response: 
{
    "priceQuote": {
        "uuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae3",
        "createdAt": "2021-03-05 16:32:20",
        "updatedAt": "2021-03-05 16:32:20",
        "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
        "vendorPrice": 49000.0,
        "taxSlab": null,
        "availableQuantity": 10.0,
        "status": "AVAILABLE",
        "vendorName": "Marvin",
        "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
    },
    "product": {
        "uuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a5f",
        "createdAt": "2021-03-05 16:25:18",
        "updatedAt": "2021-03-05 16:25:18",
        "name": "Iphone XS",
        "details": {
            "make": "Apple",
            "color": "White",
            "memory": "16GB",
            "storage": "256GB"
        },
        "status": "ACTIVE",
        "vendorProductRelations": [
            {
                "uuid": "b3085eb7-170f-437e-8722-3d9d89f1d733",
                "createdAt": "2021-03-05 16:25:35",
                "updatedAt": "2021-03-05 16:25:35",
                "vendorName": "Marvin",
                "vendorUuid": "55f2f0e7-1b83-43ef-9275-b8f15ce1d764",
                "vendorPrice": 51000.0,
                "taxSlab": null,
                "availableQuantity": 100.0,
                "status": "AVAILABLE",
                "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
            },
            {
                "uuid": "03e038b7-1af8-4dd0-895d-5f94ce373ae3",
                "createdAt": "2021-03-05 16:32:20",
                "updatedAt": "2021-03-05 16:32:20",
                "vendorName": "Marvin",
                "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
                "vendorPrice": 49000.0,
                "taxSlab": null,
                "availableQuantity": 10.0,
                "status": "AVAILABLE",
                "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
            }
        ]
    }
}
```
4. Get details of a product

```
url: GET /products/uuid
sample response: 
{
    "uuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a5f",
    "createdAt": "2021-03-05 16:25:18",
    "updatedAt": "2021-03-05 16:25:18",
    "name": "Iphone XS",
    "details": {
        "make": "Apple",
        "color": "White",
        "memory": "16GB",
        "storage": "256GB"
    },
    "basePrice": 50000.0,
    "status": "ACTIVE",
    "taxSlab": "FIVE",
    "vendorProductRelations": [
        {
            "uuid": "b3085eb7-170f-437e-8722-3d9d89f1d733",
            "createdAt": "2021-03-05 16:25:35",
            "updatedAt": "2021-03-05 16:25:35",
            "vendorUuid": "55f2f0e7-1b83-43ef-9275-b8f15ce1d764",
            "vendorPrice": 51000.0,
            "vendorName": "Marvin",
            "availableQuantity": 100.0,
            "status": "AVAILABLE",
            "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
        },
        {
            "uuid": "60aa10c5-0c86-4083-9754-d37d15e21a23",
            "createdAt": "2021-03-05 16:28:39",
            "updatedAt": "2021-03-05 16:28:39",
            "vendorUuid": "54f2f0e7-1b83-43ef-9275-b8f15ce1d764",
            "vendorPrice": 50000.0,
            "vendorName": "Marvin",
            "availableQuantity": 150.0,
            "status": "AVAILABLE",
            "vendorOriginAddressUuid": "508910d3-c1a8-4436-8e51-5d15171186c8"
        }
    ]
}
```

5. Create Order for a particular product and vendor

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
        "price": 50000,
        "taxSlab": "FIVE",
        "originAddressUuid": "77d6fddb-8dcb-4ca2-93c7-cd3350d84a3a" 
    }]
}
```

