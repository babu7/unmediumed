package unmediumed.response

import unmediumed.parse.MediumPost

import scala.collection.JavaConverters._

class Response(
    statusCode: Integer,
    body: String,
    customHeaders: Map[String, String] = Map(),
    base64Encoded: Boolean = true) {

  val commonHeaders: Map[String, String] = Map("content-type" -> "text/markdown; charset=utf-8")
  val responseHeaders: Map[String, String] = commonHeaders ++ customHeaders

  def toOutput: Output = new Output(statusCode, body, responseHeaders.asJava, base64Encoded)
}

case class OkResponse(post: MediumPost) extends Response(200, post.markdown)

case class UnprocessableEntityResponse(message: String) extends Response(422, message)

case class InternalServerErrorResponse(message: String) extends Response(500, message)

case class BadGatewayResponse(message: String) extends Response(502, message)
