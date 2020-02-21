resource "aws_lambda_function" "lambda_function" {
  function_name = "hello_world_function"
  filename      = "${var.lambda_payload_filename}"

  role             = "${aws_iam_role.lambda_apigateway_iam_role.arn}"
  handler          = "${var.lambda_function_handler}"
  source_code_hash = "${base64sha256(file(var.lambda_payload_filename))}"
  runtime          = "${var.lambda_runtime}"

  environment {
    variables = {
      currentLocation = "London"
    }
  }
}

resource "aws_lambda_permission" "lambda_function" {
  statement_id  = "AllowExecutionFromApiGateway"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.lambda_function.function_name}"
  principal     = "apigateway.amazonaws.com"
  source_arn    = "arn:aws:execute-api:${var.region}:${var.account_id}:${aws_api_gateway_rest_api.lambda_api.id}/${aws_api_gateway_deployment.lambda_deploy.stage_name}/${aws_api_gateway_integration.lambda_integration.integration_http_method}${aws_api_gateway_resource.lambda_api_gateway.path}"
}