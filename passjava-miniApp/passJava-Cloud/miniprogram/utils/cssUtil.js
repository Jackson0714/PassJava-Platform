const addStyle = (html) => {
html = html.replace(/\<img/gi, '<img style="max-width:100%;height:auto;display:block;"')

html = html.replace(/\<h1/gi, '<h1 style="border-bottom:2px solid rgb(239, 112, 96);font-size: 18px;display: inline-block;font-weight: bold;color: #222222;padding: 3px 10px 1px;border-top-right-radius: 3px;border-top-left-radius: 3px; margin-right: 3px;"')
html = html.replace(/\<h2>/gi, '<h2><span>')
html = html.replace(/\<\/h2>/gi, '</span></h2>')
html = html.replace(/\<h2/gi, '<h2 style="border-bottom: 2px solid rgb(239, 112, 96); font-size: 1.2em;margin-top: 40px; margin-bottom: 20px; font-weight: bold; color: black; margin-block-start: 0.83em; margin-block-end: 0.83em; margin-inline-start: 0px; margin-inline-end: 0px;display: block;"')
html = html.replace(/\<span/gi, '<span style="display: inline-block; font-weight: bold; background: rgb(239, 112, 96); color: #ffffff; padding: 3px 10px 1px; border-top-right-radius: 3px; border-top-left-radius: 3px; margin-right: 3px;"')

html = html.replace(/\<h3/gi, '<h3 style="border-bottom:2px solid rgb(239, 112, 96);font-size: 0.8em;display: inline-block;font-weight: bold; color: #111111;padding: 3px 10px 1px; margin-right: 3px;"')
html = html.replace(/\<blockquote/gi, '<blockquote style=" border-left: 4px solid #42b983;padding: 10px 15px; color: #777;background: rgba(66, 185, 131, .1)"')
html = html.replace(/\<pre/gi, '<div style="overflow-x: auto;white-space:nowrap"')
html = html.replace(/\<\/pre>/gi, '</div>')
html = html.replace(/\<code/gi, '<div style="white-space:nowrap;display: block; font-family: Operator Mono, Consolas, Monaco, Menlo, monospace; border-radius: 0px; font-size: 12px; -webkit-overflow-scrolling: touch;display: block; overflow-x: auto; padding: 16px; color: #abb2bf; background: #282c34;"')
html = html.replace(/\<\/code>/gi, '</div>')
html = html.replace(/(\s){2,}/g, '<br>')
html = html.replace(/\[;]/gi, '<br>')

return html
}

const convertToMarkdown = (str) => {
let markdown = str.replace(/(\s)\1{1}/g, '\r\n') //连续两个空格转换为回车换行
return markdown
}

module.exports = { addStyle, convertToMarkdown }
