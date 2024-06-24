const fs = require('fs');

const directory = '';
const prefix = 'image';
const extension = '.jpg';
const totalImages = 10;
for (let i = 1; i <= totalImages; i++) {
    const oldFileName = `${directory}${i}${extension}`;
    const newFileName = `${directory}${prefix}${i}${extension}`;
    fs.renameSync(oldFileName, newFileName);
    console.log(`Renamed ${oldFileName} to ${newFileName}`);
}
