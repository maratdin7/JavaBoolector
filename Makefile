BASE_DIR=$(shell pwd)

CC=gcc
CFLAGS=-c -Wall -fPIC -I/usr/lib/jvm/default/include -I/usr/lib/jvm/default/include/linux -I${BASE_DIR}/boolector/src
LDFLAGS=-fPIC -shared -L${TARGET_DIR} -lboolector-3.0.0

SOURCES_DIR=${BASE_DIR}/src/main/c
OBJECTS_DIR=${BASE_DIR}/target/c
TARGET_DIR=${BASE_DIR}/target/classes

EXECUTABLE=${TARGET_DIR}/libboolector-jni-3.0.0.so
BOOLECTOR_EXECUTABLE=${TARGET_DIR}/libboolector-3.0.0.so

SOURCES=$(shell find '$(SOURCES_DIR)' -type f -name '*.c')
OBJECTS=$(SOURCES:$(SOURCES_DIR)/%.c=$(OBJECTS_DIR)/%.o)

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	echo ${BASE_DIR}
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

$(OBJECTS): $(SOURCES) $(BOOLECTOR_EXECUTABLE)
	mkdir -p $(OBJECTS_DIR)
	$(CC) $(CFLAGS) $< -o $@

.ONESHELL:
boolector:
	git submodule init
	git submodule update

	cd ${BASE_DIR}/boolector
	./contrib/setup-lingeling.sh || exit 1
	./contrib/setup-btor2tools.sh || exit 1
	./configure.sh --shared --only-lingeling || exit 1
	cd build
	make || exit 1
	cd ${BASE_DIR}

$(BOOLECTOR_EXECUTABLE): boolector
	cp ${BASE_DIR}/boolector/build/lib/libboolector.so ${BOOLECTOR_EXECUTABLE}

clean:
	rm -rf $(OBJECTS_DIR) $(EXECUTABLE) $(BOOLECTOR_EXECUTABLE)