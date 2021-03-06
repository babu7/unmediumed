package unmediumed.source

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

trait HttpRequest {
  protected val Timeout: Int = 5000
  protected val UserAgent: String = "unmediumed/1"

  protected def createConnection(method: String, url: String): HttpURLConnection = {
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    connection.setRequestMethod(method)
    connection.setConnectTimeout(Timeout)
    connection.setReadTimeout(Timeout)
    connection.setRequestProperty("User-Agent", UserAgent)
    connection
  }

  def send: InputStream
}

class HttpGetRequest(val url: String) extends HttpRequest {
  val connection: HttpURLConnection = createConnection("GET", url)

  def send: InputStream = connection.getInputStream
}

class HttpPostRequest(url: String) extends HttpRequest {
  val connection: HttpURLConnection = createConnection("POST", url)
  connection.setFixedLengthStreamingMode(0)
  connection.setDoOutput(true)

  def send: InputStream = connection.getInputStream
}
