const jsonServer = require("json-server")
const server = jsonServer.create()
const router = jsonServer.router("db.json")
const middlewares = jsonServer.defaults()

// Set default middlewares (logger, static, cors and no-cache)
server.use(middlewares)

// Add custom routes before JSON Server router
server.get("/echo", (req, res) => {
  res.jsonp(req.query)
})

server.use(jsonServer.bodyParser)
server.post("/login", (req, res) => {
  switch (req.body.email) {
    case "johndoe@example.com":
      res.jsonp({
        user: {
          name: "John",
          surname: "Doe",
          email: "johndoe@example.com",
        },
        token: "superAdminToken",
      })
      break
    case "due@gmail.com":
      res.jsonp({
        user: {
          name: "Pinocchio",
          surname: "Liar",
          email: "due@gmail.com",
        },
        token: "adminToken",
      })
      break

    case "tre@gmail.com":
      res.jsonp({
        user: {
          name: "Pulcinella",
          surname: "Colors",
          email: "tre@gmail.com",
        },
        token: "user",
      })
      break
    default:
      res.status(401).jsonp({ error: "Error" })
  }
})

server.post("/validateToken", (req, res) => {
  res.jsonp(true)
})

server.post("/me", (req, res) => {
  switch (req.get("authentication")) {
    case "superAdminToken":
      res.jsonp({
        name: "John",
        surname: "Doe",
        email: "johndoe@example.com",
      })
      break
    case "adminToken":
      res.jsonp({
        name: "Pinocchio",
        surname: "Liar",
        email: "due@gmail.com",
      })
      break

    case "user":
      res.jsonp({
        name: "Pulcinella",
        surname: "Colors",
        email: "tre@gmail.com",
      })
      break
  }
})

server.use(router)
server.listen(3000, () => {
  console.log("JSON Server is running")
})
