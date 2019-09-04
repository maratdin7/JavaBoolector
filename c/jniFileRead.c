static jstring readFileContent(JNIEnv *env, FILE* file) {
    rewind(file);
    fseek(file, 0, SEEK_END);
    long size = ftell(file);
    rewind(file);

    char* fcontent = (char*) malloc(size);
    fread(fcontent, 1, size, file);
    jstring result = (*env)->NewStringUTF(env, fcontent);
    free(fcontent);
    return result;
}
