#!/bin/bash
VERSION=$1
if [[ $VERSION == '' ]]; then
  echo 'Usage:'
  echo '  $ ./build.sh VERSION'
  exit 1
fi

DIR="clonepool-$VERSION"
TAR="$DIR.tar.gz"

sbt assembly
mkdir $DIR
mv target/scala-2.11/Clonepool-assembly-$VERSION.jar $DIR/clonepool.jar
tar zcvf $TAR $DIR

shasum -a 256 $TAR | cut -f1 -d' '
rm -rf $DIR
