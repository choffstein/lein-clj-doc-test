# clj-doc-test

Take clj-doc-test and create a lein plugin for it.  
For now, will run all doc tests.
In the future, should probably also have option arguments for what files to run on.

## Usage

	lein doc-test

## Example

	$ cd plugin-test
	$ lein doc-test
	Examining plugin-test/src/plugin_test/adder.clj...
	+ Testing adder...
	FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
	expected: (clojure.core/= (adder 1 2) (quote 5))
  	  actual: (not (clojure.core/= 3 5))
	-------------------------------------------------------
	Examining plugin-test/src/plugin_test/core.clj...
	+ Testing adder...
	FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
	expected: (clojure.core/= ((adder 1) 2) (quote 4))
	  actual: (not (clojure.core/= 3 4))
	-------------------------------------------------------
	Examining plugin-test/src/plugin_test/subber.clj...
        + Testing multr...
	+ Testing subber...
	FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
	expected: (clojure.core/= (subber 1 2) (quote 4))
  	  actual: (not (clojure.core/= -1 4))
	-------------------------------------------------------

## License

Copyright (C) 2011 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
