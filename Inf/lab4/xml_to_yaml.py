# XML -> YAML Понедельник

import sys
import time

num_spaces_in_tab = 2
input_file = sys.argv[1] if len(sys.argv) >= 2 else "files/def_schedule.xml"
output_file = sys.argv[2] if len(sys.argv) >= 3 else "files/output.yaml"
time_file = sys.argv[3] if len(sys.argv) >= 4 else "files/check_time.txt"


def get_data():
    # Read input file data
    text = open(input_file, encoding="utf-8").read()

    return text.split("\n")


def process_row(row: str, result: list, cur_spaces: int, spaces_tags: dict):
    row = row.strip()

    if not row or row.startswith("<?xml"):
        return result, cur_spaces, spaces_tags
    elif row.startswith("<") and not row.startswith("</"):
        # If open tag started
        # Add tab
        cur_spaces += num_spaces_in_tab

        if cur_spaces not in spaces_tags:
            spaces_tags[cur_spaces] = []

        # Find end of tag and get him
        end_tag_index = row.index(">")
        full_tag = row[1:end_tag_index]
        self_closing = False

        # Remove / in the end of tag if he is self-closing
        if full_tag[-1] == "/":
            self_closing = True
            full_tag = full_tag[:-1]

        full_tag_info = full_tag.split()
        tag = full_tag_info.pop(0)

        # If there is second tag in current block,
        # then add previous same tag "-" before him
        if spaces_tags[cur_spaces].count(tag) == 1:
            for ind in range(len(result) - 1, -1, -1):
                previous_row = result[ind].strip()

                if previous_row.startswith(tag):
                    result.insert(ind, " " * cur_spaces + "-")
                    break

        # If current tag has already been in this block,
        # add "-" line before
        if tag in spaces_tags[cur_spaces]:
            result.append(" " * cur_spaces + "-")

        # Add new tag and update current block tags list
        result.append(" " * cur_spaces + tag + ":")
        spaces_tags[cur_spaces].append(tag)

        # If there are params with tags, for example <tag param="value">
        # Write them like "_param: value"
        # Each on new line, in block inside tag
        for param in full_tag_info:
            key, value = param.split("=")
            result.append("%s_%s: %s" % (
                " " * (cur_spaces + num_spaces_in_tab),
                key, value.replace('"', "")
            ))

        # Return spaces back if it is self-closing tag
        cur_spaces -= num_spaces_in_tab if self_closing else 0

        # If there is something after tag than recursively process him
        if end_tag_index != len(row) - 1:
            result, cur_spaces, spaces_tags = process_row(
                row[end_tag_index + 1:], result, cur_spaces, spaces_tags
            )
    elif row.startswith("</"):
        # If closing tag started
        previous_row = result[-1].strip()

        # If previous tag wasn't current open tag
        if not row.startswith(previous_row.replace(":", "")):
            # Clear last block
            spaces_tags[cur_spaces + num_spaces_in_tab] = []
        else:
            result.pop()
        # Decrease spaces
        cur_spaces -= num_spaces_in_tab

        # If there is something after tag than recursively process him
        end_tag_index = row.index(">")
        if end_tag_index != len(row) - 1:
            result, cur_spaces, spaces_tags = process_row(
                row[end_tag_index + 1:], result, cur_spaces, spaces_tags
            )
    else:
        # If text started
        end_index = row.index("<") if "<" in row else len(row)
        # Write text just like "- text"
        result.append(
            " " * (cur_spaces + num_spaces_in_tab) +
            "- " + row[:end_index]
        )

        # If there is something after text process it
        if end_index != len(row):
            result, cur_spaces, spaces_tags = process_row(
                row[end_index:], result, cur_spaces, spaces_tags
            )

    return result, cur_spaces, spaces_tags


def process(first_row):
    result = []
    cur_spaces = -2
    spaces_tags = {}
    row = first_row

    while row is not None:
        # Process new row
        result, cur_spaces, spaces_tags = process_row(
            row, result, cur_spaces, spaces_tags
        )
        # Get new row from generator
        row = (yield)

    # Write result lines in output file
    with open(output_file, "w", encoding="utf-8") as f:
        f.write("\n".join(result))

    yield


def main():
    data = get_data()

    if len(data) > 0:
        main_process = process(data.pop(0))
        next(main_process)

        for row in data:
            main_process.send(row)

        main_process.send(None)


if __name__ == '__main__':
    start_time = time.time()

    for _ in range(10):
        main()


    with open(time_file, "a") as file:
        file.write(
            "---- Test One ----\n%s sec\n" % (
                time.time() - start_time
            )
        )
