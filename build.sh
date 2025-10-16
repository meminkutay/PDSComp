#!/bin/bash

# Exit immediately on error
set -e

# Configuration
APP_NAME="PDSComp"
MAIN_CLASS="com.pdsdataextractor.MainApplication"
JAVA_FX_PATH="$HOME/Downloads/javafx-sdk-21.0.7/lib"
JAR_NAME="${APP_NAME}.jar"
TARGET_DIR="target"
OUT_DIR="out"
VERSION="1.0"

echo "🔨 Cleaning and building project with Maven..."
mvn clean package

echo "✅ Maven build complete: $TARGET_DIR/$JAR_NAME"

# Remove any previous output
rm -rf "$OUT_DIR"

echo "📦 Creating macOS .app using jpackage (system JDK + JavaFX jars)..."
jpackage \
  --type app-image \
  --name "$APP_NAME" \
  --input "$TARGET_DIR" \
  --main-jar "$JAR_NAME" \
  --main-class "$MAIN_CLASS" \
  --java-options "--module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml -Dprism.order=sw" \
  --dest "$OUT_DIR"

echo "✅ .app bundle created at: $OUT_DIR/${APP_NAME}.app"

# Prompt for optional DMG creation
read -p "📦 Do you want to create a .dmg installer as well? [y/N]: " make_dmg
if [[ "$make_dmg" =~ ^[Yy]$ ]]; then
  echo "🗳  Creating .dmg installer..."
jpackage \
  --type app-image \
  --name "$APP_NAME" \
  --input "$TARGET_DIR" \
  --main-jar "$JAR_NAME" \
  --main-class "$MAIN_CLASS" \
  --java-options "--module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml -Dprism.order=sw" \
  --dest "$OUT_DIR"

  echo "✅ .dmg installer created at: $OUT_DIR/${APP_NAME}-${VERSION}.dmg"
fi

echo "🎉 Done! You can now launch the app from $OUT_DIR/${APP_NAME}.app"
