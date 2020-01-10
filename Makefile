BASE_DIR=$(shell pwd)

PREFIX=/usr

CC=gcc
CFLAGS=-c -Wall -fPIC -I/usr/lib/jvm/default/include -I/usr/lib/jvm/default/include/linux -I/usr/include/boolector
LDFLAGS=-fPIC -shared -lboolector -lbtor2parser

SOURCES_DIR=${BASE_DIR}/src/main/c
OBJECTS_DIR=${BASE_DIR}/target/c
TARGET_DIR=${BASE_DIR}/lib
EXECUTABLE_NAME=libboolector-java.so
EXECUTABLE=${TARGET_DIR}/$(EXECUTABLE_NAME)

SOURCES=$(shell find '$(SOURCES_DIR)' -type f -name '*.c')
OBJECTS=$(SOURCES:$(SOURCES_DIR)/%.c=$(OBJECTS_DIR)/%.o)

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS) headers
	mkdir -p $(TARGET_DIR)
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

$(OBJECTS): $(SOURCES)
	mkdir -p $(OBJECTS_DIR)
	$(CC) $(CFLAGS) $< -o $@

headers:
	javah -d $(SOURCES_DIR) -classpath $(BASE_DIR)/src/main/java -force org.jetbrains.research.boolector.Native

clean:
	rm -rfi $(OBJECTS_DIR) $(EXECUTABLE)

.PHONY: install
install: $(EXECUTABLE)
	mkdir -p $(DESTDIR)$(PREFIX)/lib
	mkdir -p $(DESTDIR)$(PREFIX)/include/boolector-java
	cp $(EXECUTABLE) $(DESTDIR)$(PREFIX)/lib/$(EXECUTABLE_NAME)
	cp $(SOURCES_DIR)/*.h $(DESTDIR)$(PREFIX)/include/boolector-java

.PHONY: uninstall
uninstall:
	rm -f $(DESTDIR)$(PREFIX)/lib/$(EXECUTABLE_NAME)
	rm -rf $(DESTDIR)$(PREFIX)/include/boolector-java

