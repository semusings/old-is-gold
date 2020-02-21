resource "aws_dynamodb_table" "lambda-dynamo" {
  name = "PSStudent"
  read_capacity = 20
  write_capacity = 20
  hash_key = "studentId"
  attribute {
    name = "studentId"
    type = "S"
  }
}