from pyswip import Prolog

prolog = Prolog()

prolog.consult("script1.pl")

valid_answer = {
    "y": True,
    "n": False,
    "Y": True,
    "N": False,
    "yes": True,
    "no": False,
    "Yes": True,
    "No": False,
    "YES": True,
    "NO": False
}

def get_value(question):
    res = list(prolog.query(question))
    if len(res) == 1:
        return res[0]['X']
    return res

def get_inventory():
    """Получает инвентарь из Prolog"""
    inventory = []
    for row in prolog.query("inventory(Inventory)"):
        inventory = row["Inventory"]

    out={}
    for elem in inventory:
        buff = list(elem.split(','))
        out[buff[1][1:]] = int(float(buff[-1][1:-1]))

    return out  # Возвращаем найденный инвентарь

def get_resources():
    """Получает список доступных ресурсов из Prolog"""
    resources = []
    for row in prolog.query("resource(Resource)"):
        resources.append(row["Resource"])  # Извлекаем значение "Resource" из результата
    return resources


def get_available_crafts():
    """Получает список доступных крафтов"""
    crafts = []
    for row in prolog.query("recipe(Item, _, _)"):
        crafts.append(row["Item"])  # Извлекаем значение "Item" из результата
    return crafts

def craft_item(item, num):
    for _ in range(num):
        result = list(prolog.query(f"once(craft({item}, default))."))
        if not result:
            print(f"Не удалось создать {item}. Проверьте наличие необходимых ресурсов.")
            break  # Выходим из цикла, если крафт не удался

def check_crafts(crafts):
    out = {}
    for craft in crafts:
        # Проверяем доступность крафта
        result = list(prolog.query(f'is_available({craft}).'))
        if result:
            num = get_value(f"max_creatable({craft},X).")
            out[craft] = int(float(num))
    return out

def formated_map_print(arr, title):
    print(title)
    for key in arr.keys():
        print(f"\t{key}:\t{arr[key]}")


def ask_about_craft(available_crafts):
    user_input = ""
    while user_input not in valid_answer:
        user_input = input("Вы хотите создать предмет?(y/n)\n> ")
        if user_input in valid_answer:
            if not valid_answer[user_input]: return
            while True:
                user_input = input("Введите выбранный предмет в формате \"название количество\" \n> ")
                a, b = user_input.split(" ")
                if available_crafts.get(a):
                    if int(b) > available_crafts[a]: print(f"Вы не можете создать больше {available_crafts[a]} единиц")
                    else:
                        craft_item(a, int(b))
                        return
        else:
            print("Неправильный ответ(y/n)")

def ask_about_mine():
    user_input = ""
    while user_input not in valid_answer:
        user_input = input("Вы хотите добыть ресурсов?(y/n)\n> ")
        if user_input in valid_answer:
            if not valid_answer[user_input]: return
            resources = get_resources()
            print("Список доступных для добычи ресурсов:", resources)
            while True:
                user_input = input("Введите выбранный ресурс в формате \"название\" \n> ")
                if user_input in resources:
                    _ = list(prolog.query(f"mine({user_input}, default, 10)."))
                    return
        else:
            print("Неправильный ответ(y/n)")

while(True):
    print("\n-----------------------")
    inventory = get_inventory()
    formated_map_print(inventory, "Ваш инвентарь:")

    available_crafts = get_available_crafts()
    available_crafts = check_crafts(available_crafts)
    if len(available_crafts) > 0:
        formated_map_print(available_crafts, "\nДоступные крафты:")
        ask_about_craft(available_crafts)
    else:
        print("У вас недостаточно ресурсов для крафта! \n Вы можете добыть недостающие ресурсы...")
    ask_about_mine()




