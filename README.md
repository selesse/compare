# compare

Command line tool for comparing file contents. There are two modes, "file" and
"directory".

## Quick start

```bash
$ ./gradlew installDist
./build/install/compare/bin/compare
```

## File mode

File mode takes in a list of directories and recursively computes the md5sum
of all files. It then reports which files have the same md5sum.  This might be
useful if, for example, you're going through several years of disorganized
folders to find duplicate photos.

You can specify as many directories as you want in this mode.

```
$ build/install/compare/bin/compare --type=file ~/git/jxlint
Same file contents found in multiple locations:
  /Users/alex/git/jxlint/jxlint-cli/src/test/java/com/selesse/jxlint/settings/JxlintProgramSettings.java
  /Users/alex/git/jxlint/jxlint/src/test/java/com/selesse/jxlint/settings/JxlintProgramSettings.java

Same file contents found in multiple locations:
  /Users/alex/git/jxlint/jxlint-cli/src/test/resources/logback-test.xml
  /Users/alex/git/jxlint/jxlint/src/test/resources/logback-test.xml

Same file contents found in multiple locations:
  /Users/alex/git/jxlint/jxlint-cli/src/test/resources/samplerules/xml/UniqueAttributeTestPass
  /Users/alex/git/jxlint/jxlint-cli/src/test/resources/samplerules/xml/XmlEncodingTestPass
  /Users/alex/git/jxlint/jxlint-cli/src/test/resources/samplerules/xml/XmlVersionTestPass

Same file contents found in multiple locations:
  /Users/alex/git/jxlint/jxlint-impl/src/test/resources/logback.xml
  /Users/alex/git/jxlint/jxlint-maven/src/test/resources/test-logback.xml
```

## Directory mode

Directory mode takes in two directories, and reports the differences between
the two folders. Specifically, it will print files that are only in the first
folder, only in the second folder, and files that are in both but differ in
content. Relative paths are considered.

```
$ build/install/compare/bin/compare --type=directory ~/git/rails-project1 ~/git/rails-project2
/Users/alex/git/rails-project1 => 45 directories, 91 files
/Users/alex/git/rails-project2 => 51 directories, 118 files
Only in rails-project1
  A - /Users/alex/git/rails-project1/app/controllers/basic_controller.rb
Only in rails-project2
  B - /Users/alex/git/rails-project2/.gitlab-ci.yml
  B - /Users/alex/git/rails-project2/app/controllers/products_controller.rb
  B - /Users/alex/git/rails-project2/app/models/pricewatch.rb
  B - /Users/alex/git/rails-project2/app/models/product.rb
  B - /Users/alex/git/rails-project2/app/views/products/index.html.erb
  B - /Users/alex/git/rails-project2/app/views/products/new.html.erb
  B - /Users/alex/git/rails-project2/app/views/products/show.html.erb
  B - /Users/alex/git/rails-project2/bin/setup-heroku
  B - /Users/alex/git/rails-project2/db/migrate/20190819233617_create_products.rb
  B - /Users/alex/git/rails-project2/db/migrate/20190819234353_create_pricewatches.rb
  B - /Users/alex/git/rails-project2/test/fixtures/pricewatches.yml
  B - /Users/alex/git/rails-project2/test/fixtures/products.yml
  B - /Users/alex/git/rails-project2/test/fixtures/users.yml
  B - /Users/alex/git/rails-project2/test/models/pricewatch_test.rb
  B - /Users/alex/git/rails-project2/test/models/product_test.rb
Files in both folders that differ:
  Gemfile
  Gemfile.lock
  app/models/user.rb
  config/routes.rb
  config/spring.rb
  db/schema.rb
```
