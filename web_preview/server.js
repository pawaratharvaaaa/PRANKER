const http = require("http");
const fs = require("fs");
const path = require("path");

const PORT = 3000;
const RAW_VIDEO_PATH = path.join(__dirname, "..", "app", "src", "main", "res", "raw", "rickroll.mp4");
const INDEX_HTML_PATH = path.join(__dirname, "index.html");

const server = http.createServer((req, res) => {
    const parsedUrl = new URL(req.url, `http://localhost:${PORT}`);
    const pathname = parsedUrl.pathname;

    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Headers", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, OPTIONS");

    if (req.method === "OPTIONS") {
        res.writeHead(200);
        return res.end();
    }

    // 1. Media Stream
    if (pathname === "/media/rickroll.mp4" && (req.method === "GET" || req.method === "HEAD")) {
        if (!fs.existsSync(RAW_VIDEO_PATH)) {
            res.writeHead(404, { "Content-Type": "text/plain" });
            return res.end("Audio not found");
        }

        const stat = fs.statSync(RAW_VIDEO_PATH);
        const fileSize = stat.size;
        const range = req.headers.range;

        if (req.method === "HEAD") {
            res.writeHead(200, {
                "Content-Length": fileSize,
                "Content-Type": "audio/mp4",
                "Accept-Ranges": "bytes",
            });
            return res.end();
        }

        if (range) {
            const parts = range.replace(/bytes=/, "").split("-");
            const start = parseInt(parts[0], 10);
            const end = parts[1] ? parseInt(parts[1], 10) : fileSize - 1;
            const chunksize = (end - start) + 1;
            const file = fs.createReadStream(RAW_VIDEO_PATH, { start, end });
            res.writeHead(206, {
                "Content-Range": `bytes ${start}-${end}/${fileSize}`,
                "Accept-Ranges": "bytes",
                "Content-Length": chunksize,
                "Content-Type": "audio/mp4",
            });
            file.pipe(res);
        } else {
            res.writeHead(200, {
                "Content-Length": fileSize,
                "Content-Type": "audio/mp4",
                "Accept-Ranges": "bytes",
            });
            fs.createReadStream(RAW_VIDEO_PATH).pipe(res);
        }
        return;
    }

    // 2. Serve HTML
    if (pathname === "/" || pathname === "/index.html") {
        fs.readFile(INDEX_HTML_PATH, (err, data) => {
            if (err) {
                res.writeHead(500, { "Content-Type": "text/plain" });
                return res.end("Error");
            }
            res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
            res.end(data);
        });
        return;
    }

    res.writeHead(404, { "Content-Type": "text/plain" });
    res.end("Not Found");
});

server.listen(PORT, () => {
    console.log(`Server listening on http://localhost:${PORT}`);
});
