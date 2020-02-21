variable "profile" {
  default = "default"
}
variable "aws_access_key" {}
variable "aws_secret_key" {}
variable "region" {}
variable "account_id" {}

variable "lambda_payload_filename" {
  default = "../pathsala-serverless-backend/build/distributions/pathsala-serverless-backend-1.0-SNAPSHOT.zip"
}

variable "lambda_function_handler" {
  default = "pathsala.serverless.student.RegisterStudentHandler"
}

variable "lambda_runtime" {
  default = "java8"
}

variable "api_path" {
  default = "students"
}

variable "student_manager_http_method" {
  default = "POST"
}

variable "api_env_stage_name" {
  default = "beta"
}
