#!/bin/bash
while getopts n:d: flag
do
    case "${flag}" in
        n) name=${OPTARG};;
        d) description=${OPTARG};;
    esac
done

if [ -n "$name" ]
then
    mkdir $name
    cd $name
else 
    name=${PWD##*/}
fi

cat << EOF > package.json
{
  "name": "$name",
  "version": "1.0.0",
  "description": "$description",
  "author": "Alejandro Pastor",
  "license": "MIT",
  "scripts": {
    "tsc": "tsc",
    "start": "ts-node src/main.ts",
    "lint": "eslint . --ext .ts --fix"
  },
  "devDependencies": {},
  "dependencies": {}
}
EOF

cat << EOF > tsconfig.json
{
  "compilerOptions": {
    "target": "es5",                                     /* Set the JavaScript language version for emitted JavaScript and include compatible library declarations. */
    "module": "commonjs",                                /* Specify what module code is generated. */
    "rootDir": "./src",                                  /* Specify the root folder within your source files. */
    "baseUrl": "./",                                  /* Specify the base directory to resolve non-relative module names. */
    "sourceMap": true,                                /* Create source map files for emitted JavaScript files. */
    "outDir": "./dist",                                   /* Specify an output folder for all emitted files. */
    "esModuleInterop": true,                             /* Emit additional JavaScript to ease support for importing CommonJS modules. This enables `allowSyntheticDefaultImports` for type compatibility. */
    "forceConsistentCasingInFileNames": true,            /* Ensure that casing is correct in imports. */
    "strict": true,                                      /* Enable all strict type-checking options. */
    "skipLibCheck": true                                 /* Skip type checking all .d.ts files. */
  }
}
EOF

cat << EOF > .eslintrc
{
  "root": true,
  "parser": "@typescript-eslint/parser",
  "plugins": [
    "@typescript-eslint"
  ],
  "extends": [
    "eslint:recommended",
    "plugin:@typescript-eslint/eslint-recommended",
    "plugin:@typescript-eslint/recommended"
  ],
  "rules": {}
}
EOF

cat << EOF > .eslintignore
node_modules
dist
EOF

yarn add -D typescript tsc @types/node ts-node eslint @typescript-eslint/parser @typescript-eslint/eslint-plugin
yarn add tslog dotenv

mkdir -p src


echo "Project created!"
