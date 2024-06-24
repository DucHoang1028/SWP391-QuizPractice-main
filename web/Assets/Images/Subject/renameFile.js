const fs = require('fs');

const directory = '';
const prefix = 'thumb';
const extension = '.jpg';
const totalImages = 50;
for (let i = 1; i <= totalImages; i++) {
    const oldFileName = `${directory}thumb (${i})${extension}`;
    const newFileName = `${directory}${prefix}${i}${extension}`;
    fs.renameSync(oldFileName, newFileName);
    console.log(`Renamed ${oldFileName} to ${newFileName}`);
}
