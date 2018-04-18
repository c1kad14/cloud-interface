"use strict";

module.exports = {
    entry: [
       // 'eventsource-polyfill', // necessary for hot reloading with IE
     //   'webpack-hot-middleware/client?reload=true', //note that it reloads the page if hot module reloading fails.
        "./scripts/index.js"
    ],
    output: {
        filename: "./scripts/bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: "babel-loader",
                exclude: /node_modules/,
                query: {
                    presets: ["es2015", "react"]
                }
            }
        ]
    }
};
