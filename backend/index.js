const express = require('express');
const dotenv = require('dotenv');

dotenv.config();

const app = express();

const functions = require('./routes/routes');

app.use('/', functions);

app.listen(process.env.PORT, () => {
    console.log('ShopNest server run on port -> ', process.env.PORT);
});