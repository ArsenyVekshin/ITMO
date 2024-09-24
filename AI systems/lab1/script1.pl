:- dynamic inventory/1.

% базовые ресурсы
resource(wood).
resource(coil).
resource(stone).
resource(iron).
resource(copper).

% ресурсы 1ур
item(iron_plate).
item(copper_plate).

% ресурсы 2ур
item(wire).
item(iron_rod).
item(gear).
item(circuit).

% способы крафта
crafter(default).
crafter(craft_machine_1).
crafter(craft_machine_2).
crafter(craft_machine_3).

% скорость крафта
crafter_speed(default, 1.0).
crafter_speed(craft_machine_1, 0.5).
crafter_speed(craft_machine_2, 0.75).
crafter_speed(craft_machine_3, 1.25).

% способы добычи
miner(default).
miner(burner_drill).
miner(electric_drill).

% скорость добычи
miner_speed(default, 1.0).
miner_speed(burner_drill, 0.25).
miner_speed(electric_drill, 0.5).

% рецепты крафта
recipe(iron_plate, [(iron, 1)], 3.2).
recipe(copper_plate, [(iron, 1)], 3.2).
recipe(wire, [(copper_plate, 1)], 0.5).
recipe(iron_rod, [(iron_plate, 5)], 16.0).
recipe(gear, [(iron_plate, 2)], 0.5).
recipe(circuit, [(wire, 2), (iron_plate, 1)], 0.5).

% Начальный инвентарь
inventory( [
       (iron_plate, 10),
       (iron_beam, 10),
       (copper_plate, 10)
   ]).

% Печать инвентаря
print_inventory :-
    inventory(Inventory),
    format(' Inventory:\n'),
    print_inventory_items(Inventory).

print_inventory_items([]).
print_inventory_items([(Item, Count)|Rest]) :-
    format('  ~w: ~w\n', [Item, Count]),
    print_inventory_items(Rest).

% Функция для добавления элемента
add_item(Item, Count) :-
    inventory(Inventory),
    update_inventory(Item, Count, Inventory, NewInventory),
    retract(inventory(Inventory)),
    assert(inventory(NewInventory)).

% Обновление инвентаря
update_inventory(Item, Count, [], [(Item, Count)]).
update_inventory(Item, Count, [(Item, CurrentCount)|Rest], [(Item, NewCount)|Rest]) :-
    NewCount is CurrentCount + Count.
update_inventory(Item, Count, [Other|Rest], [Other|NewRest]) :-
    update_inventory(Item, Count, Rest, NewRest).

% Функция добычи ресурсов
mine(Resource, Miner, Time) :-
    miner_speed(Miner, MineSpeed),
    Mined is Time*MineSpeed,
    add_item(Resource, Mined).


% Функция крафта
craft(Item, Crafter) :-
    recipe(Item, Ingredients, Time),
    check_ingredients(Ingredients),
    remove_ingredients(Ingredients),
    craft_time(Crafter, Time, CraftTime),
    format('The item ~w was successfully created by ~w... (time: ~w)\n', [Item, Crafter, CraftTime]),
    add_item(Item, 1).


% вспомогательная функция для проверки инвентаря
check_ingredients([]).
check_ingredients([(Item, Quantity) | Tail]) :-
    has_ingredients(Item, Quantity),
    check_ingredients(Tail).

% Проверка наличия ингредиентов
has_ingredients(Item, Quantity) :-
    inventory(Items),
    member((Item, Available), Items),
    Available >= Quantity.

% Удаление ингредиентов
remove_ingredients([]).
remove_ingredients([(Item_t, Count_t) | Tail]) :-
    remove_item(Item_t, Count_t),
    remove_ingredients(Rest).

% Удаление предмета из инвентаря
remove_item(Item, Count) :-
    inventory(Inventory),
    remove_item_helper(Item, Count, Inventory, NewInventory),
    retract(inventory(Inventory)),  
    assert(inventory(NewInventory)).

% Вспомогательный предикат для удаления элемента
remove_item_helper(_, 0, Inventory, Inventory). 
remove_item_helper(Item, Count, [(Item, CurrentCount)|Rest], [(Item, NewCount)|Rest]) :-
    NewCount is CurrentCount - Count, 
    NewCount >= 0.
remove_item_helper(Item, Count, [Other|Rest], [Other|NewRest]) :-
    remove_item_helper(Item, Count, Rest, NewRest). 

% Вычисление времени крафта
craft_time(Crafter, Time, CraftTime) :-
    crafter_speed(Crafter, Speed),
    CraftTime is Time / Speed.
