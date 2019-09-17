BASE_DIR=$(shell pwd)

CC=gcc
CFLAGS=-c -Wall -fPIC -I/usr/lib/jvm/default/include -I/usr/lib/jvm/default/include/linux -I${BASE_DIR}/boolector/src
LDFLAGS=-fPIC -shared -L${TARGET_DIR} -lboolector-3.0.0

SOURCES_DIR=${BASE_DIR}/src/main/c
OBJECTS_DIR=${BASE_DIR}/target/c
TARGET_DIR=${BASE_DIR}/target/classes
BOOLECTOR_DIR=${BASE_DIR}/boolector

EXECUTABLE=${TARGET_DIR}/libboolector-jni-3.0.0.so
BOOLECTOR_EXECUTABLE=${TARGET_DIR}/libboolector-3.0.0.so

SOURCES=$(shell find '$(SOURCES_DIR)' -type f -name '*.c')
OBJECTS=$(SOURCES:$(SOURCES_DIR)/%.c=$(OBJECTS_DIR)/%.o)

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

$(OBJECTS): $(SOURCES)
	mkdir -p $(OBJECTS_DIR)
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -rfi $(OBJECTS_DIR) $(EXECUTABLE)
