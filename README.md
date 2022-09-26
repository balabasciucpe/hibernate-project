# hibernate-project
A project using Hibernate capabilities

I  used modules to try, learn and exercise Hibernate, working thru errors, bugs and getting here, finally.

Works like this:
- in Postman, you can go and create a Product:


```
http://localhost:8080/products/createProduct
{
    "productDescription" : 
    {
        "descriptionName" : "Colebil",
        "descriptionAbout" : "testam"
    },
    "productPrice" : 19.99,
    "productQuantity" : 30
}
```

then go and create a Category for that Product:

```
http://localhost:8080/categories/createCategory
{
    "categoryDescription" : 
    {
        "descriptionName" : "Biotics",
        "descriptionAbout" : "about testam"
    }
}
```

then add that product in recently created Category:
```
http://localhost:8080/categories/addProducts/Biotics/Colebil

{
    "categoryId": 1,
    "categoryVersion": 1,
    "categoryDescription": {
        "descriptionName": "Biotics",
        "descriptionAbout": "about testam"
    },
    "productCollection": [
        {
            "productId": 1,
            "productPrice": 19.99,
            "productQuantity": 30,
            "productDescription": {
                "descriptionName": "Colebil",
                "descriptionAbout": "testam"
            },
            "productStatus": "PRODUCT_AVAILABLE",
            "productExpDate": "2023-02-28",
            "productProducedAtDate": "2022-09-25T19:01:25.872+00:00"
        }
    ],
    "promotion": null
}
```

But now you need a Store to sell these products, no? and for that you use Categories of Products in Store, here we go:
```
http://localhost:8080/store/createStore
{
    "storeLocation" : 
    {
        "locationCity" : "Suceava",
        "locationCountry" : "Romania",
        "locationZipCode" : "124314"
    },
    "storeType" : "MEDICAL_STORE",
    "storeName" : "Catena"
}
```

now add Categories in this Store:
```
http://localhost:8080/store/addCategory/Catena/Biotics

{
    "storeId": 1,
    "storeLocation": {
        "locationCity": "Suceava",
        "locationCountry": "Romania",
        "locationZipCode": {
            "zipCode": "124314"
        }
    },
    "storeName": "Catena",
    "storeType": "MEDICAL_STORE",
    "categorySet": [
        {
            "categoryId": 1,
            "categoryVersion": 2,
            "categoryDescription": {
                "descriptionName": "Biotics",
                "descriptionAbout": "about testam"
            },
            "productCollection": [
                {
                    "productId": 1,
                    "productPrice": 19.99,
                    "productQuantity": 30,
                    "productDescription": {
                        "descriptionName": "Colebil",
                        "descriptionAbout": "testam"
                    },
                    "productStatus": "PRODUCT_AVAILABLE",
                    "productExpDate": "2023-02-28",
                    "productProducedAtDate": "2022-09-25T19:01:25.872+00:00"
                }
            ],
            "promotion": null
        }
    ],
    "promotion": null
}

```

What meaning stories have if customers doesn't exists at all?
-problem solved, create a Customer:
```
http://localhost:8080/customers/createCustomer
{
    "customerAddress" : 
    {
        "customerDetails" : 
        {
            "customerName"  : "Petrea",
            "customerPrename"  : "hohoho"
        },
    "addressCity" : "Suceava",
    "addressCountry" : "Romania",
    "addressEmail" : "facaca@afadfda.com"
    },
    "amountToSpend" : "100"
}
```

now go to Store:
```
http://localhost:8080/customers/goIn/Catena/Petrea

{
    "customerId": 100,
    "customerAddress": {
        "customerDetails": {
            "customerName": "Petrea",
            "customerPrename": "hohoho"
        },
        "addressCity": "Suceava",
        "addressCountry": "Romania",
        "addressEmail": "facaca@afadfda.com"
    },
    "purchasedDetails": null,
    "productCollection": [],
    "store": {
        "storeId": 1,
        "storeLocation": {
            "locationCity": "Suceava",
            "locationCountry": "Romania",
            "locationZipCode": {
                "zipCode": "124314"
            }
        },
        "storeName": "Catena",
        "storeType": "MEDICAL_STORE",
        "categorySet": [
            {
                "categoryId": 1,
                "categoryVersion": 3,
                "categoryDescription": {
                    "descriptionName": "Biotics",
                    "descriptionAbout": "about testam"
                },
                "productCollection": [
                    {
                        "productId": 1,
                        "productPrice": 19.99,
                        "productQuantity": 30,
                        "productDescription": {
                            "descriptionName": "Colebil",
                            "descriptionAbout": "testam"
                        },
                        "productStatus": "PRODUCT_AVAILABLE",
                        "productExpDate": "2023-02-28",
                        "productProducedAtDate": "2022-09-25T19:01:25.872+00:00"
                    }
                ],
                "promotion": null
            }
        ],
        "promotion": null
    },
    "amountToSpend": 100
}
```

and buy something
```
http://localhost:8080/customers/buyProducts/Catena/Colebil/Petrea/4

{
    "amountToSpend": 20,
    "productCollection": [
        {
            "productDescription": {
                "descriptionName": "Colebil",
                "descriptionAbout": "testam"
            },
            "productQuantity": 4,
            "productPrice": 79.96,
            "productExpDate": "2023-02-28",
            "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
        }
    ]
}

-> look what you purchased right now
{
    "customerId": 100,
    "customerAddress": {
        "customerDetails": {
            "customerName": "Petrea",
            "customerPrename": "hohoho"
        },
        "addressCity": "Suceava",
        "addressCountry": "Romania",
        "addressEmail": "facaca@afadfda.com"
    },
    "purchasedDetails": {
        "purchasedItemName": "Colebil",
        "purchasedItemQuantity": 4,
        "purchasedItemPrice": 79.96
    },
    "productCollection": [
        {
            "productId": 1,
            "productPrice": 19.99,
            "productQuantity": 26,
            "productDescription": {
                "descriptionName": "Colebil",
                "descriptionAbout": "testam"
            },
            "productStatus": "PRODUCT_AVAILABLE",
            "productExpDate": "2023-02-28",
            "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
        }
    ],
    "store": {
        "storeId": 1,
        "storeLocation": {
            "locationCity": "Suceava",
            "locationCountry": "Romania",
            "locationZipCode": {
                "zipCode": "124314"
            }
        },
        "storeName": "Catena",
        "storeType": "MEDICAL_STORE",
        "categorySet": [
            {
                "categoryId": 1,
                "categoryVersion": 4,
                "categoryDescription": {
                    "descriptionName": "Biotics",
                    "descriptionAbout": "about testam"
                },
                "productCollection": [
                    {
                        "productId": 1,
                        "productPrice": 19.99,
                        "productQuantity": 26,
                        "productDescription": {
                            "descriptionName": "Colebil",
                            "descriptionAbout": "testam"
                        },
                        "productStatus": "PRODUCT_AVAILABLE",
                        "productExpDate": "2023-02-28",
                        "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
                    }
                ],
                "promotion": null
            }
        ],
        "promotion": null
    },
    "amountToSpend": 20
}


but be carefull at how much money you have:
Sorry, you need to refill your wallet or we need to refill our stock, come later
```

you can even create a Promotion for that Store and all Products from all Categories:
```
http://localhost:8080/promotions/createPromotion
http://localhost:8080/store/addPromotionToStore/Catena/Christmas/12

{
    "storeId": 1,
    "storeLocation": {
        "locationCity": "Suceava",
        "locationCountry": "Romania",
        "locationZipCode": {
            "zipCode": "124314"
        }
    },
    "storeName": "Catena",
    "storeType": "MEDICAL_STORE",
    "categorySet": [
        {
            "categoryId": 1,
            "categoryVersion": 5,
            "categoryDescription": {
                "descriptionName": "Biotics",
                "descriptionAbout": "about testam"
            },
            "productCollection": [
                {
                    "productId": 1,
                    "productPrice": 15.83,
                    "productQuantity": 26,
                    "productDescription": {
                        "descriptionName": "Colebil",
                        "descriptionAbout": "testam"
                    },
                    "productStatus": "PRODUCT_AVAILABLE",
                    "productExpDate": "2023-02-28",
                    "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
                }
            ],
            "promotion": {
                "promotionId": 1,
                "numberOfProductsAtPromotion": 26,
                "promotionSeason": {
                    "promotion": "easterPromotion",
                    "nowYouHaveToPayLessForEasterWith": 15.83
                },
                "productList": [
                    {
                        "productId": 1,
                        "productPrice": 15.83,
                        "productQuantity": 26,
                        "productDescription": {
                            "descriptionName": "Colebil",
                            "descriptionAbout": "testam"
                        },
                        "productStatus": "PRODUCT_AVAILABLE",
                        "productExpDate": "2023-02-28",
                        "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
                    }
                ]
            }
        }
    ],
    "promotion": {
        "promotionId": 2,
        "numberOfProductsAtPromotion": 26,
        "promotionSeason": {
            "promotion": "christmasPromotion",
            "newPriceToPayForChristmasIs": 15.83
        },
        "productList": [
            {
                "productId": 1,
                "productPrice": 15.83, //heh
                "productQuantity": 26,
                "productDescription": {
                    "descriptionName": "Colebil",
                    "descriptionAbout": "testam"
                },
                "productStatus": "PRODUCT_AVAILABLE",
                "productExpDate": "2023-02-28",
                "productProducedAtDate": "2022-09-26T18:06:18.755+00:00"
            }
        ]
    }
}

```

or just for a particular Category from Store:
```
http://localhost:8080/categories/addPromotionToCategory/Biotics/Easter/10  

{
    "categoryId": 1,
    "categoryVersion": 3,
    "categoryDescription": {
        "descriptionName": "Biotics",
        "descriptionAbout": "about testam"
    },
    "productCollection": [
        {
            "productId": 1,
            "productPrice": 15.83,
            "productQuantity": 30,
            "productDescription": {
                "descriptionName": "Colebil",
                "descriptionAbout": "testam"
            },
            "productStatus": "PRODUCT_AVAILABLE",
            "productExpDate": "2023-02-28",
            "productProducedAtDate": "2022-09-26T18:23:48.177+00:00"
        }
    ],
    "promotion": {
        "promotionId": 2,
        "numberOfProductsAtPromotion": 30,
        "promotionSeason": {
            "promotion": "easterPromotion",
            "newPriceForChristmasIs": 15.83
        },
        "productList": [
            {
                "productId": 1,
                "productPrice": 15.83,
                "productQuantity": 30,
                "productDescription": {
                    "descriptionName": "Colebil",
                    "descriptionAbout": "testam"
                },
                "productStatus": "PRODUCT_AVAILABLE",
                "productExpDate": "2023-02-28",
                "productProducedAtDate": "2022-09-26T18:23:48.177+00:00"
            }
        ]
    }
}
```

[my notes are here](https://docs.google.com/document/d/1aGgk4rfZvRoNEtSh1Nox07c1qbOSSh3zREbXjp5jWZ8/edit?usp=sharing)

