const express = require('express');
const router = express.Router();

const test = require('./../functions/test/test');

router.get('/testServer', (req, res) => {
    test(req, res);
})

module.exports = router;