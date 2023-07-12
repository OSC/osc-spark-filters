# OSC Spark Filters

This jar was developed to provide authentication mechanism to [apache spark](https://spark.apache.org/).

## Usage

Boot spark up with an environment variable `SPARK_UI_AUTH_TOKEN`. 
Now in the UI, set a cookie `spark_ui_auth_token` to that token's value.
Now requests made to the spark UI will be authenticated.