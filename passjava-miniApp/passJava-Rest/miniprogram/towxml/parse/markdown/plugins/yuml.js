const config = require('../../../config');
const mermaidChart = (code) => {
    return `<yuml value="${encodeURIComponent(code)}"></yuml>`;
}

module.exports = md => {
    const temp = md.renderer.rules.fence.bind(md.renderer.rules)
    md.renderer.rules.fence = (tokens, idx, options, env, slf) => {
        const token = tokens[idx]
        const code = token.content.trim();
        if (token.info === 'yuml') {
            return mermaidChart(code)
        };
        // const firstLine = code.split(/\n/)[0].trim()
        // if (firstLine === 'gantt' || firstLine === 'sequenceDiagram' || firstLine.match(/^graph (?:TB|BT|RL|LR|TD);?$/)) {
        //     return mermaidChart(code)
        // }
        return temp(tokens, idx, options, env, slf)
    }
};