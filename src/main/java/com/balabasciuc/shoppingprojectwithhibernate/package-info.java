@org.hibernate.annotations.GenericGenerator(
        name = "ID_GENERATOR", strategy = "enhanced-sequence",
        parameters =
                {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "HIBERNATE_PROJECT_SEQUENCE"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "100")
                }
)

@org.hibernate.annotations.GenericGenerator(
        name = "STORE_ID_GENERATOR", strategy = "enhanced-sequence",
        parameters =
                {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "HIBERNATE_STORE_SEQUENCE"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1")
                }
)

@org.hibernate.annotations.GenericGenerator(
        name = "PRODUCT_ID_GENERATOR", strategy = "enhanced-sequence",
        parameters =
                {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "HIBERNATE_PRODUCT_SEQUENCE"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1")
                }
)

@org.hibernate.annotations.GenericGenerator(
        name = "CATEGORY_ID_GENERATOR", strategy = "enhanced-sequence",
        parameters =
                {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "HIBERNATE_CATEGORY_ID"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1")
                }
)

@org.hibernate.annotations.GenericGenerator(
        name = "CUSTOMER_ID_GENERATOR", strategy = "enhanced-sequence",
        parameters =
                {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "HIBERNATE_CUSTOMER_ID"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1")
                }
)

package com.balabasciuc.shoppingprojectwithhibernate;