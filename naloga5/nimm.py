import functools

MISERE = 'misere'
NORMAL = 'normal'


def nim(heaps, game_type):
    count = 0
    print(game_type, heaps, end=' ')

    is_misere = game_type == MISERE

    is_near_endgame = False
    count_non_0_1 = sum(1 for x in heaps if x > 1)
    is_near_endgame = (count_non_0_1 <= 1)

    # nim sum will give the correct end-game move for normal play but
    # misere requires the last move be forced onto the opponent
    if is_misere and is_near_endgame:
        moves_left = sum(1 for x in heaps if x > 0)
        is_odd = (moves_left % 2 == 1)
        sizeof_max = max(heaps)
        index_of_max = heaps.index(sizeof_max)

        if sizeof_max == 1 and is_odd:
            return "You will lose :("

        # reduce the game to an odd number of 1's
        return index_of_max, sizeof_max - int(is_odd)
    print(heaps)
    print('\n')
    nim_sum = functools.reduce(lambda x, y: x ^ y, heaps)

    print(nim_sum)
    print('\n')
    if nim_sum == 0:
        return "You will lose :("

    # Calc which move to make
    for index, heap in enumerate(heaps):
        target_size = heap ^ nim_sum
        count += 1
        if target_size < heap:
            amount_to_remove = heap - target_size
            return index, amount_to_remove, count


if __name__ == "__main__":
    import doctest
    doctest.testmod()

print(nim([2, 2, 2], MISERE))
