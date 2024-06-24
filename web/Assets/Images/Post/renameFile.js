const fs = require('fs');

const directory = '';
const prefix = 'thumb';
const extension = '.jpg';
const totalImages = 10;
const start = 11;
const end = 41;

for (let i = start; i <= end; i++) {
    const previousFileName = `${directory}${prefix}${i - 10}${extension}`;
    const newFileName = `${directory}${prefix}${i}${extension}`;
    fs.copyFileSync(previousFileName, newFileName);
    console.log(`Copied ${previousFileName} to ${newFileName}`);
}

