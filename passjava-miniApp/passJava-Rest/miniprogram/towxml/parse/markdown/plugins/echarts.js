module.exports = md => {
    const temp = md.renderer.rules.fence.bind(md.renderer.rules)
    md.renderer.rules.fence = (tokens, idx, options, env, slf) => {
        const token = tokens[idx]
        const code = token.content.trim();
        if (token.info === 'echarts') {
            return `<echarts value="${encodeURIComponent(code)}"></echarts>`;
        };
        return temp(tokens, idx, options, env, slf)
    }
};