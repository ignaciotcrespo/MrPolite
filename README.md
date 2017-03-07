![Mr Polite Logo](https://raw.githubusercontent.com/ignaciotcrespo/MrPolite/master/designs/mrpolite.jpg)


![build status](https://img.shields.io/badge/build-info =>-yellow.svg) [![Build Status](https://travis-ci.org/ignaciotcrespo/MrPolite.svg?branch=master)](https://travis-ci.org/ignaciotcrespo/MrPolite) [![codecov.io](http://codecov.io/github/ignaciotcrespo/MrPolite/coverage.svg?branch=master)](http://codecov.io/github/ignaciotcrespo/MrPolite?branch=master) [![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ignaciotcrespo/MrPolite/blob/master/LICENSE)

# Mr Polite
Mr Polite will give you anything you need, if you ask him in a polite way.

Let's say you have a class `Animal` and you want to generate an instance of it:
```java
Animal animal = MrPolite.one(Animal.class).please();
```
Mr Polite will create the `Animal` for you and populate with random data. He is so good with you. Very important, you need to be gentle with Mr Polite, remember to say please at the end.
 
 Ok, what if you need many animals? Let's see another example:
 ```java
List<Animal> animals = MrPolite.aListOf(15, Animal.class).please();
```

Mr Polite will create 15 animals for you. Nice, but sometimes you already have the instance and just want to change some fields. Let's take a look:
```java
MrPolite.change(animal)
   .withFieldEqualTo("type", "cat")
   .please();
```

Notice you must say which field needs to be changed using `withFieldEqualTo("type", "cat")`. MrPolite will not change anything in the object unless you specify what needs to be changed. He doesn't want to be rude and change everything without being asked for it.

# Polite desires

Mr Polite can do much more than just populating random data. You can express to him your desires, and you will be pleased.
```java
MrPolite.one(Animal.class)
   .withDepth(1)
   .withFieldEqualTo("type", "cat")
   .withClassEqualTo(Integer.class, 25)
   .withCollectionSizeRange(3, 10)
   .withGenerics(Long.class)
   .withFieldImageLink("\\w*[iI]mage\\w*", 200, 300)
   .withFieldNamesInStrings()
   .withNumberRange(1000, 2000)
   .withStringsMaxLength(10)
   .please();
```

For more details about these parameters, please refer to the [javadocs](https://raw.githubusercontent.com/ignaciotcrespo/MrPolite/master/docs/javadocs/com/github/ignaciotcrespo/mrpolite/PoliteDesire.html) section.

# Current Version
* The current stable version is `1.0.3`

# Android support
MrPolite works fine in android, it is compiled with JDK 1.6 and tested on real projects.

# Adding Mr Polite to your project
Using gradle: 
```properties
compile 'com.github.ignaciotcrespo:mrpolite:1.0.3'
```

# Contribution
Mr Polite is a work in progress, it is stable but of course there are still some edge cases not covered.

You are welcome to contribute to the project, feel free to create a pull request with your changes.

For questions, suggestions or feedback, create an issue in this repository.

# License

Mr Polite is released under the [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT).

```
The MIT License

Copyright (c) 2017, Ignacio Tomas Crespo (itcrespo@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
