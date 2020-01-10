#include <assert.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "org_jetbrains_research_boolector_Native.h"
#include "boolector.h"
#include "btoropt.h"

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

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_btor(JNIEnv *env, jobject jobj) {
    Btor* btor = boolector_new();
    boolector_set_opt(btor, BTOR_OPT_MODEL_GEN, 1);
    boolector_set_opt(btor, BTOR_OPT_INCREMENTAL, 1);
    return (jlong) btor;
}

JNIEXPORT void JNICALL
Java_org_jetbrains_research_boolector_Native_assertForm(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    boolector_assert(btor, node);
}

JNIEXPORT void JNICALL Java_org_jetbrains_research_boolector_Native_assume(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    boolector_assume(btor, node);
}

JNIEXPORT jint JNICALL Java_org_jetbrains_research_boolector_Native_getSat(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    return (jint) boolector_sat(btor);
}

JNIEXPORT jint JNICALL Java_org_jetbrains_research_boolector_Native_simplify(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    return (jint) boolector_simplify(btor);
}

JNIEXPORT jstring JNICALL Java_org_jetbrains_research_boolector_Native_printModel(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    FILE *tmp_model = tmpfile();
    boolector_print_model(btor, "smt2", tmp_model);
    jstring model = readFileContent(env, tmp_model);
    fclose(tmp_model);
    return model;
}

JNIEXPORT jstring JNICALL Java_org_jetbrains_research_boolector_Native_dumpSmt2(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    FILE *tmp_dump = tmpfile();
    boolector_dump_smt2(btor, tmp_dump);
    jstring model = readFileContent(env, tmp_dump);
    fclose(tmp_dump);
    return model;
}

JNIEXPORT void JNICALL Java_org_jetbrains_research_boolector_Native_btorRelease(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    boolector_release_all(btor);
    assert (boolector_get_refs(btor) == 0);
    boolector_delete(btor);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_var(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref, jstring jsymbol) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node;
    BoolectorSort sort = (BoolectorSort) jsort_ref;

    const char *str = (*env)->GetStringUTFChars(env, jsymbol, 0);
    node = strncmp(str, "nullINc", 7) == 0 ?
                boolector_var(btor, sort, NULL) :
                boolector_var(btor, sort, str);
    (*env)->ReleaseStringUTFChars(env, jsymbol, str);
    return (jlong) node;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_matchNodeByName(JNIEnv *env, jobject jobj, jlong btorRef, jstring jsymbol) {
    Btor* btor = (Btor*) btorRef;
    const char *str = (*env)->GetStringUTFChars(env, jsymbol, 0);
    BoolectorNode *node = boolector_match_node_by_symbol(btor, str);
    (*env)->ReleaseStringUTFChars(env, jsymbol, str);
    return (jlong) node;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_constInt(JNIEnv *env, jobject jobj, jlong btorRef, jint num, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort sort = (BoolectorSort) jsort_ref;
    return (jlong) boolector_int(btor, num, sort);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_constBitvec(JNIEnv *env, jobject jobj, jlong btorRef, jstring jsymbol) {
    Btor* btor = (Btor*) btorRef;
    const char *bits = (*env)->GetStringUTFChars(env, jsymbol, 0);
    jlong ans = (jlong) boolector_const(btor, bits);
    (*env)->ReleaseStringUTFChars(env, jsymbol, bits);
    return ans;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_constLong(JNIEnv *env, jobject jobj, jlong btorRef, jstring jsymbol, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort sort = (BoolectorSort) jsort_ref;
    const char *bits = (*env)->GetStringUTFChars(env, jsymbol, 0);
    jlong ans = (jlong) boolector_constd(btor, sort, bits);
    (*env)->ReleaseStringUTFChars(env, jsymbol, bits);
    return ans;
}

JNIEXPORT jstring JNICALL
Java_org_jetbrains_research_boolector_Native_getBits(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    const char *bits = boolector_get_bits(btor, node);
    return (*env)->NewStringUTF(env, bits);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_bitvecAssignment(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    int size = boolector_get_width(btor, node);
    const char *bits = boolector_bv_assignment(btor, node);
    long long number = 0;
    long long power = 1;
    for (int i = size - 1; i >= 0; i--) {
        if (bits[i] == '1') number += power;
        power *= 2;
    }
    boolector_free_bv_assignment(btor, bits);
    return (jlong) number;
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_constNodeTrue(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    return (jlong) boolector_true(btor);
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_constNodeFalse(JNIEnv *env, jobject jobj, jlong btorRef) {
    Btor* btor = (Btor*) btorRef;
    return (jlong) boolector_false(btor);
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_zero(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort sort = (BoolectorSort) jsort_ref;
    return (jlong) boolector_zero(btor, sort);
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_not(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_not(btor, node);
}

#define BINARY_OP(opcode) \
    JNIEXPORT jlong JNICALL \
    Java_org_jetbrains_research_boolector_Native_##opcode(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_first_ref, \
                                                     jlong jnode_second_ref) { \
        Btor* btor = (Btor*) btorRef; \
        BoolectorNode *first_node = (BoolectorNode *) jnode_first_ref; \
        BoolectorNode *second_node = (BoolectorNode *) jnode_second_ref; \
        return (jlong) boolector_##opcode(btor, first_node, second_node); \
    } \

BINARY_OP(add)
BINARY_OP(sub)
BINARY_OP(mul)
BINARY_OP(sdiv)
BINARY_OP(udiv)
BINARY_OP(smod)
BINARY_OP(urem)

BINARY_OP(and)
BINARY_OP(or)
BINARY_OP(xor)

BINARY_OP(eq)
BINARY_OP(sgt)
BINARY_OP(sgte)
BINARY_OP(ugt)
BINARY_OP(slt)
BINARY_OP(slte)
BINARY_OP(sll)
BINARY_OP(srl)
BINARY_OP(sra)


BINARY_OP(implies)
BINARY_OP(iff)
BINARY_OP(concat)

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_neg(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_neg(btor, node);
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_copy(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_copy(btor, node);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_sext(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref, jint width) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_sext(btor, node, (uint32_t) width);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_uext(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref, jint width) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_uext(btor, node, (uint32_t) width);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_slice(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref, jint upper, jint lower) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_slice(btor, node, (uint32_t) upper, (uint32_t) lower);
}

JNIEXPORT jint JNICALL
Java_org_jetbrains_research_boolector_Native_getWidthNode(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jint) boolector_get_width(btor, node);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_array(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref, jstring jsymbol) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort sort = (BoolectorSort) jsort_ref;
    const char *str = (*env)->GetStringUTFChars(env, jsymbol, 0);
    jlong ans;
    if (strncmp(str, "nullINc", 7) == 0) {
        ans = (jlong) boolector_array(btor, sort, NULL);
    } else { ans = (jlong) boolector_array(btor, sort, str); }
    (*env)->ReleaseStringUTFChars(env, jsymbol, str);
    return ans;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_read(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_array_ref,
                                                  jlong jnode_index_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *array = (BoolectorNode *) jnode_array_ref;
    BoolectorNode *index = (BoolectorNode *) jnode_index_ref;
    return (jlong) boolector_read(btor, array, index);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_write(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_array_ref,
                                                   jlong jnode_index_ref,
                                                   jlong jnode_value_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *array = (BoolectorNode *) jnode_array_ref;
    BoolectorNode *index = (BoolectorNode *) jnode_index_ref;
    BoolectorNode *value = (BoolectorNode *) jnode_value_ref;
    return (jlong) boolector_write(btor, array, index, value);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_cond(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_cond_ref,
                                                  jlong jnode_first_ref,
                                                  jlong jnode_second_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *bool_node = (BoolectorNode *) jnode_cond_ref;
    BoolectorNode *first_node = (BoolectorNode *) jnode_first_ref;
    BoolectorNode *second_node = (BoolectorNode *) jnode_second_ref;
    return (jlong) boolector_cond(btor, bool_node, first_node, second_node);
}

JNIEXPORT jint JNICALL Java_org_jetbrains_research_boolector_Native_getId(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jint) boolector_get_node_id(btor, node);
}

JNIEXPORT jstring JNICALL
Java_org_jetbrains_research_boolector_Native_getSymbol(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    const char *symbol = boolector_get_symbol(btor, node);
    return (symbol != NULL) ? (*env)->NewStringUTF(env, symbol) : NULL;
}

JNIEXPORT void JNICALL
Java_org_jetbrains_research_boolector_Native_releaseNode(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    boolector_release(btor, node);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_getSort(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    return (jlong) boolector_get_sort(btor, node);
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_bitvecSort(JNIEnv *env, jobject jobj, jlong btorRef, jint width) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort s = boolector_bitvec_sort(btor, (uint32_t) width);
    return (jlong) s;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_arraySort(JNIEnv *env, jobject jobj, jlong btorRef, jlong index_sort_ref,
                                                       jlong element_sort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort index = (BoolectorSort) index_sort_ref;
    BoolectorSort element = (BoolectorSort) element_sort_ref;
    return (jlong) boolector_array_sort(btor, index, element);
}

JNIEXPORT jboolean JNICALL
Java_org_jetbrains_research_boolector_Native_isBitvecSort(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort s = (BoolectorSort) jsort_ref;
    return (jboolean) boolector_is_bitvec_sort(btor, s);
}

JNIEXPORT jboolean JNICALL
Java_org_jetbrains_research_boolector_Native_isArraySort(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort s = (BoolectorSort) jsort_ref;
    return (jboolean) boolector_is_array_sort(btor, s);
}

JNIEXPORT void JNICALL
Java_org_jetbrains_research_boolector_Native_releaseSort(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort s = (BoolectorSort) jsort_ref;
    boolector_release_sort(btor, s);
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_param(JNIEnv *env, jobject jobj, jlong btorRef, jlong jsort_ref, jstring jsymbol) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node;
    BoolectorSort sort = (BoolectorSort) jsort_ref;
    const char *str = (*env)->GetStringUTFChars(env, jsymbol, 0);
    if (strncmp(str, "nullINc", 7) == 0) {
        node = boolector_param(btor, sort, NULL);
    } else {
        node = boolector_param(btor, sort, str);
    }
    (*env)->ReleaseStringUTFChars(env, jsymbol, str);
    return (jlong) node;
}

BoolectorNode **ref_array_to_type(long *refs, uint32_t size) {
    BoolectorNode **array = malloc(size);
    for (int i = 0; i < size; i++) {
        array[i] = (BoolectorNode *) refs[i];
    }
    return array;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_fun(JNIEnv *env, jobject jobj, jlong btorRef, jlongArray jparams, jint jlength,
                                                 jlong body_node_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *body_node = (BoolectorNode *) body_node_ref;
    uint32_t size = (uint32_t) jlength;
    long *ref_params = (*env)->GetLongArrayElements(env, jparams, 0);
    BoolectorNode **params = ref_array_to_type(ref_params, size);
    jlong ans = (jlong) boolector_fun(btor, params, (uint32_t) size, body_node);
    (*env)->ReleaseLongArrayElements(env, jparams, ref_params, 0);
    free(params);
    return ans;
}

JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_forAll(JNIEnv *env, jobject jobj, jlong btorRef, jlongArray jparams, jint jlength,
                                                    jlong body_node_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *body_node = (BoolectorNode *) body_node_ref;
    uint32_t size = (uint32_t) jlength;
    long *ref_params = (*env)->GetLongArrayElements(env, jparams, 0);
    BoolectorNode **params = ref_array_to_type(ref_params, size);
    jlong ans = (jlong) boolector_forall(btor, params, (uint32_t) size, body_node);
    (*env)->ReleaseLongArrayElements(env, jparams, ref_params, 0);
    free(params);
    return ans;
}


JNIEXPORT jlong JNICALL
Java_org_jetbrains_research_boolector_Native_apply(JNIEnv *env, jobject jobj, jlong btorRef, jlongArray jargs, jint jlength,
                                                   jlong fun_node_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *fun_node = (BoolectorNode *) fun_node_ref;
    uint32_t size = (uint32_t) jlength;
    long *ref_args = (*env)->GetLongArrayElements(env, jargs, 0);
    BoolectorNode **args = ref_array_to_type(ref_args, size);
    jlong ans = (jlong) boolector_apply(btor, args, (uint32_t) size, fun_node);
    (*env)->ReleaseLongArrayElements(env, jargs, ref_args, 0);
    free(args);
    return ans;
}

JNIEXPORT jint JNICALL
Java_org_jetbrains_research_boolector_Native_kindNode(JNIEnv *env, jobject jobj, jlong btorRef, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    BoolectorSort sort = boolector_get_sort(btor, node);
    bool isBitvecSort = boolector_is_bitvec_sort(btor, sort);
    bool isBoolSort = isBitvecSort && boolector_get_width(btor, node) == 1;
    if (boolector_is_const(btor, node)) return isBoolSort ? 0 : 1;
    if (boolector_is_array(btor, node)) return 2;
    if (isBoolSort) return 3;
    if (isBitvecSort) return 4;
    return 5;
}

JNIEXPORT jlong JNICALL Java_org_jetbrains_research_boolector_Native_constArray(JNIEnv *env, jobject jobj, jlong btorRef,
                                                                                jlong jsort_array_ref,
                                                                                jlong jsort_index_ref,
                                                                                jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorSort sort_array = (BoolectorSort) jsort_array_ref;
    BoolectorNode *array = boolector_array(btor, sort_array, NULL);
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    BoolectorSort sort_index = (BoolectorSort) jsort_index_ref;
    double size = 255;
    BoolectorNode *index;
    for (int i = 0; i < size; i++) {
        index = boolector_int(btor, i, sort_index);
        array = boolector_write(btor, array, index, node);
    }
    return (jlong) array;
}

JNIEXPORT jint JNICALL
Java_org_jetbrains_research_boolector_Native_getIndexWidth(JNIEnv *env, jobject jobj, jlong btorRef, jlong jarray_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *array_node = (BoolectorNode *) jarray_ref;
    int width_index = boolector_get_index_width(btor, array_node);
    return width_index;
}


JNIEXPORT jboolean JNICALL
Java_org_jetbrains_research_boolector_Native_boolectorAssert(JNIEnv *env, jobject jobj, jlong btorRef, jstring jans, jlong jnode_ref) {
    Btor* btor = (Btor*) btorRef;
    BoolectorNode *node = (BoolectorNode *) jnode_ref;
    const char *ans = (*env)->GetStringUTFChars(env, jans, 0);
    const char *str = boolector_bv_assignment(btor, node);
    bool a = strncmp(str, ans, boolector_get_width(btor, node)) == 0;
    if (!a) printf("Expect:\n %s\n JavaBoolector:\n%s", ans, str);
    boolector_free_bv_assignment(btor, str);
    (*env)->ReleaseStringUTFChars(env, jans, ans);
    fflush(stdout);
    return (jboolean) a;
}






