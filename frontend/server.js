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
    case "superadmin@example.com":
      res.jsonp({
        user: {
          name: "John",
          surname: "Doe",
          email: "johndoe@example.com",
        },
        token: "superAdminToken",
      })
      break
    case "manager@example.com":
      res.jsonp({
        user: {
          name: "Pinocchio",
          surname: "Liar",
          email: "due@gmail.com",
        },
        token: "manager",
      })
      break

    case "user@example.com":
      res.jsonp({
        user: {
          name: "Pulcinella",
          surname: "Colors",
          email: "tre@gmail.com",
        },
        token: "employee",
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
        role: 0,
      })
      break
    case "manager":
      res.jsonp({
        name: "Pinocchio",
        surname: "Liar",
        email: "due@gmail.com",
        role: 1,
      })
      break

    case "employee":
      res.jsonp({
        name: "Pulcinella",
        surname: "Colors",
        email: "tre@gmail.com",
        role: 2,
      })
      break
  }
})

server.get("/getManager", (req, res) => {
  res.jsonp([
    {
      id: "1",
      first_name: "John",
      last_name: "Doe",
      date_of_birth: "1980-01-01",
      email: "johndoe@example.com",
      department_id: "1",
      benefits: ["1", "2"],
    },
    {
      id: "2",
      first_name: "Jane",
      last_name: "Smith",
      date_of_birth: "1985-02-15",
      email: "janesmith@example.com",
      department_id: "2",
    },
  ])
})

server.get("/getDepartments", (req, res) => {
  res.jsonp([
    {
      department_id: "1",
      name: "Human Resources",
      description: "Handles company recruitment, policy, and employee relations.",
      manager_id: "1",
    },
    {
      department_id: "2",
      name: "IT",
      description: "Responsible for managing technology and computer systems.",
      manager_id: "2",
    },
    {
      department_id: "3",
      name: "Marketing",
      description: "Focuses on advertising and promoting the company's products or services.",
      manager_id: "3",
    },
  ])
})

server.use(router)
server.listen(3000, () => {
  console.log("JSON Server is running")
})
