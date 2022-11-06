import time
import sys
import xmlplain

input_file = sys.argv[1] if len(sys.argv) >= 2 else "files/def_schedule.xml"
output_file = sys.argv[2] if len(sys.argv) >= 3 else "files/output_lib.yaml"
time_file = sys.argv[3] if len(sys.argv) >= 4 else "files/check_time.txt"

def main():
    # Read to plain object
    with open(input_file, encoding="utf-8") as inf:
        root = xmlplain.xml_to_obj(inf, strip_space=True, fold_dict=True)

    # Output plain YAML
    with open(output_file, "w", encoding="utf-8") as outf:
        xmlplain.obj_to_yaml(root, outf)



if __name__ == '__main__':
    start_time = time.time()

    main()

    with open(time_file, "a") as file:
        file.write(
            "---- Test Lib ----\n%s sec\n" % (
                    time.time() - start_time
            )
        )