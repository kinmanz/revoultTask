import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import servlets.AllRequestsServlet

object Main {

    private val DEFAULT_JETTY_PORT = 8090

    private val port: Int
        get() = Integer.getInteger("revoult-task.port", DEFAULT_JETTY_PORT)!!

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server(port)

        val servletContextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
        servletContextHandler.addServlet(ServletHolder(AllRequestsServlet()), "/*")
        server.handler = servletContextHandler

        server.start()
        server.join()
    }
}
