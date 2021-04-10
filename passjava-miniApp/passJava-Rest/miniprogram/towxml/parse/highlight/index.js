const config = require('../../config'),
    hljs = require('./highlight');

config.highlight.forEach(item => {
    hljs.registerLanguage(item, require(`./languages/${item}`));
});

module.exports = hljs;