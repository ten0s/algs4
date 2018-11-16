.PHONY: compile test

all:
	@echo "usage: make <compile | test> CLASS=className"
	@echo "usage: make <run> CLASS=className ARGS=\"arg1 arg2 ... argN\""

compile:
	@./compile.sh "$$CLASS"

run: compile
	@./run.sh "$$CLASS" "$$ARGS"

test: compile
	@./test.sh "$$CLASS"
