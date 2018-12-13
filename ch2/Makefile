.PHONY: compile debug test

all:
	@echo "usage: make <compile-all> | <test-all>"
	@echo "usage: make <compile | test> CLASS=className"
	@echo "usage: make <run | debug> CLASS=className ARGS=\"arg1 arg2 ... argN\""

compile:
	@./compile.sh "$$CLASS"

compile-all:
	@./compile-all.sh

run: compile
	@./run.sh "$$CLASS" "$$ARGS"

debug: compile
	@./debug.sh "$$CLASS" "$$ARGS"

test: compile
	@./test.sh "$$CLASS"

test-all:
	@./test-all.sh
