# Flat JSON API

All response data must be returned as a SRE (Standard Response Entity) @todo: add description for SRE.

Reasons for flat & JSON API listing:
- We can reuse the same endpoint on multiple places and save cpu time and IO on the backed with filtering the returned
data
- We don't need to create deep changes in the code (and mostly in the tests) for modification
- It's easy to write in TDD and easy to test it
- Services aren't connected to each other, expect in one method where we resolve the request relations
(SRP likes it)
- All of our complex requests will came from PK based small selectd instead of the JOIN hell
- Very easy to handle on client side

##### Get a company without any request relation

URI `GET:/company/`

Response:

```
{
    "data": {
        "company": {
            "id": 1,
            "name": "First Company",
            "companySizeId": 3,
            "itSizeId": 4,
            "logoPath": "folder/1.jpg"
        },
        "companyStatistic": null,
        "companyGroups": null,
        "companyAddresses": null,
        "companyReviews": null
    },
    "success": true,
    "errorCode": 0,
    "requestId": "1"
}
```

Explanation:

This GET request simply request a record from the company table, and there is no any related data connected.

##### Get a company with one request relation

URI: `/company/1?requestRelationIds=1`

Response:

```
{
    "data": {
        "company": {
            "id": 1,
            "name": "Test company 1",
            "companySizeId": 1,
            "itSizeId": 5,
            "logoPath": "folder/1.jpg"
        },
        "companyStatistic": {
            "companyId": 1,
            "stackCount": 3,
            "teamsCount": 3,
            "reviewCount": 0,
            "technologiesCount": 0
        },
        "companyGroups": null,
        "companyAddresses": null,
        "companyReviews": null
    },
    "success": true,
    "errorCode": 0,
    "requestId": "1"
}
```

Explanation:

If anywhere on the site we need a company with related data like now the companyStatistic, we can simply add a
parameter called requestRelationIds, and from the Enum we can select the ID for the requested data relation.

##### Get a company with more than one request relations

Response:

URI: `/company/1?requestRelationIds=1&equestRelationIds=2`

```
{
    "data": {
        "company": {
            "id": 1,
            "name": "Test company 1",
            "companySizeId": 1,
            "itSizeId": 5,
            "logoPath": "folder/1.jpg"
        },
        "companyStatistic": {
            "companyId": 1,
            "stackCount": 3,
            "teamsCount": 3,
            "reviewCount": 0,
            "technologiesCount": 0
        },
        "companyGroups": [
            {
                "recursiveGroup": {
                    "id": 1,
                    "name": "Level 0 - Company",
                    "companyId": 1,
                    "parentId": null,
                    "depth": 1,
                    "path": "1"
                },
                "children": [
                    {
                        "recursiveGroup": {
                            "id": 2,
                            "name": "Level 1 - Team",
                            "companyId": 1,
                            "parentId": 1,
                            "depth": 2,
                            "path": "1>2"
                        }
                    }
                ]
            }
        ],
        "companyAddresses": null,
        "companyReviews": null
    },
    "success": true,
    "errorCode": 0,
    "requestId": "1"
}
```

Explanation:

This example is the same as the previous expect one thing: it is using more than one request relation in one
request. In this case we get back all requested data. If it's necessary we can select all available data for the
company.

##### Search companies with more than one request relation

URI: `/company?seekId=1&limit=2&requestRelationIds=1&requestRelationIds=2`

Response:
```
{
    "data": {
        "companies": [
            {
                "id": 1,
                "name": "Test company 1",
                "companySizeId": 1,
                "itSizeId": 5,
                "logoPath": "folder/1.jpg"
            },
            {
                "id": 2,
                "name": "Test company with long name 2",
                "companySizeId": 3,
                "itSizeId": 4,
                "logoPath": "folder/2.jpg"
            }
        ],
        "companyGroups": {
            "1": [
                {
                    "recursiveGroup": {
                        "id": 1,
                        "name": "Level 0 - Company",
                        "companyId": 1,
                        "parentId": null,
                        "depth": 1,
                        "path": "1"
                    },
                    "children": [
                    ]
                }
            ]
        },
        "paginator": [],
        "newSeekId": null,
        "companyStatistics": {
            "1": {
                "companyId": 1,
                "stackCount": 3,
                "teamsCount": 3,
                "reviewCount": 0,
                "technologiesCount": 0
            },
            "2": {
                "companyId": 2,
                "stackCount": 0,
                "teamsCount": 0,
                "reviewCount": 0,
                "technologiesCount": 0
            },
        },
        "companyAddresses": {},
        "companyReviews": {}
    },
    "success": true,
    "errorCode": 0,
    "requestId": "1"
}
```

Explanation:

In this endpoint we request 2 companies with 2 request relation ids. As we see we have a _flat list_, related data
aren't included in the company entities. They are in other list. If the client wants to select the related information
to display it, it's really from the returned key based list, where the key is always the PK, in the case the companyId.
