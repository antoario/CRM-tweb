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

// To handle POST, PUT and PATCH you need to use a body-parser
// You can use the one used by JSON Server
server.use(jsonServer.bodyParser)
server.post("/login", (req, res) => {
  // Verifica delle credenziali
  if (req.body.email === "johndoe@example.com") {
    // Se l'email è corretta, restituisce una risposta fittizia
    res.jsonp({
      id: 1,
      name: "John",
      surname: "Doe",
      email: "johndoe@example.com",
      phone_number: "+391234567890",
      password: "hashed_password_1",
      role: "manager",
      token: "polpette"
    })
  } else {
    // Se l'email non è corretta, restituisce un errore di autenticazione
    res.status(401).jsonp({ error: "Errore di autenticazione" })
  }
})
// Use default router
server.use(router)
server.listen(3000, () => {
  console.log("JSON Server is running")
})
