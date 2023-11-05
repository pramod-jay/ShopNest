const connection = require('./../../service/connection');

module.exports = async function testFunc(req, res){
    return res.json('Respond from shopnest server');
}