const jsonServer = require('json-server');
const path       = require('path');

const server      = jsonServer.create();
const router      = jsonServer.router(path.join(__dirname, 'db.json'));
const middlewares = jsonServer.defaults({ static: __dirname });

server.use(middlewares);
server.use(jsonServer.bodyParser);

// ── POST /auth/login ──────────────────────────────────────────────────────────
server.post('/auth/login', (req, res) => {
  const { email, password } = req.body || {};
  if (!email || !password) {
    return res.status(400).json({ success: false, message: 'Email and password are required.' });
  }
  const user = router.db.get('users').find({ email, password }).value();
  if (user) {
    const { password: _p, ...safeUser } = user;
    return res.json({ success: true, user: safeUser });
  }
  res.status(401).json({
    success: false,
    message: 'Login was unsuccessful. Please correct the errors and try again.'
  });
});

// ── POST /auth/register ───────────────────────────────────────────────────────
server.post('/auth/register', (req, res) => {
  const { email, password, firstName, lastName } = req.body || {};
  if (!email || !password || !firstName || !lastName) {
    return res.status(400).json({ success: false, message: 'All fields are required.' });
  }
  if (password.length < 6) {
    return res.status(400).json({ success: false, message: 'Password must have at least 6 characters.' });
  }
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    return res.status(400).json({ success: false, message: 'Please enter a valid email address.' });
  }
  if (router.db.get('users').find({ email }).value()) {
    return res.status(409).json({ success: false, message: 'An account with this email already exists.' });
  }
  const newUser = { id: Date.now(), email, password, firstName, lastName, address: null };
  router.db.get('users').push(newUser).write();
  const { password: _p, ...safeUser } = newUser;
  res.status(201).json({ success: true, user: safeUser });
});

// ── Cart qty validation ───────────────────────────────────────────────────────
server.use((req, res, next) => {
  if (['POST', 'PUT'].includes(req.method) && req.path.startsWith('/cart')) {
    const qty = Number(req.body && req.body.qty);
    if (req.body && req.body.qty !== undefined && (isNaN(qty) || qty <= 0)) {
      return res.status(400).json({ success: false, message: 'Quantity must be greater than 0.' });
    }
  }
  next();
});

// ── json-server CRUD routes ───────────────────────────────────────────────────
server.use(router);

const PORT = process.env.PORT || 3001;
server.listen(PORT, () => {
  console.log('\n  Demo Web Shop\n');
  console.log(`  Frontend  ->  http://localhost:${PORT}`);
  console.log(`  API base  ->  http://localhost:${PORT}\n`);
  console.log('  Available endpoints for Postman:\n');
  console.log(`    GET    /products`);
  console.log(`    GET    /products/:id`);
  console.log(`    GET    /products?name_like=Laptop`);
  console.log(`    POST   /auth/login          { email, password }`);
  console.log(`    POST   /auth/register       { firstName, lastName, email, password }`);
  console.log(`    GET    /users/:id`);
  console.log(`    PATCH  /users/:id`);
  console.log(`    GET    /cart?sessionId=xxx`);
  console.log(`    POST   /cart                { sessionId, productId, name, price, qty }`);
  console.log(`    PUT    /cart/:id            { ...item, qty }`);
  console.log(`    DELETE /cart/:id`);
  console.log(`    POST   /orders              { sessionId, items, billingAddress, ... }`);
  console.log(`    GET    /orders`);
  console.log(`    GET    /orders?userId=1`);
  console.log('\n  Demo credentials: demo@webshop.com / demo123\n');
});
