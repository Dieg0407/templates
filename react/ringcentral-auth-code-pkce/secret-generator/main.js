const crypto = require("crypto");

const bytes = crypto.randomBytes(32)
  .toString('base64')
  .replace(/\+/g, '-')
  .replace(/\//g, '_')
  .replace(/=/g, '');

const codeChallenge = crypto.createHash('sha256')
  .update(bytes)
  .digest()
  .toString('base64')
  .replace(/\+/g, '-')
  .replace(/\//g, '_')
  .replace(/=/g, '');

console.table([["bytes", bytes], ["challenge", codeChallenge]])