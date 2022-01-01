private static boolean BFS(ArrayList<ArrayList<Integer>> povezave, int zac,
            int konc, int stNodes, int pred[], int razdalja[]) {
        // navaden queue za BFS algoritem
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // oznacimoi node, ki smo jih ze obiskali
        boolean smoze[] = new boolean[stNodes];

        // nastavimo, da nismo obiskali ze nobenga noda(ceprau je deefault ze to), pa da
        // je od enega noda do nasledngea distance neskoncxno in da nima predhodnika se.
        // to spreminjamo pole v loop v
        for (int i = 0; i < stNodes; i++) {
            smoze[i] = false;
            razdalja[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // ker se lotimo na nodu z id zac oznacimo da smo tu ze bili oznacimio da je
        // razdalja o sm 0(ker je start) in ga sddodamoo v que in se odpravimo v zankico
        smoze[zac] = true;
        razdalja[zac] = 0;
        queue.add(zac);

        // BFS
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < povezave.get(u).size(); i++) {
                if (smoze[povezave.get(u).get(i)] == false) {
                    smoze[povezave.get(u).get(i)] = true;
                    // no comment za gor

                    // razdalja do tega vozlisca je enaka razdalji do vozlisca v katerem smo + 1,
                    // saj se tja premaknemo das ist ja logisch
                    razdalja[povezave.get(u).get(i)] = razdalja[u] + 1;
                    // zapisemo si, da v tem nodu ki ga dodamo v queue je biu prejsni node enak nodu
                    // v katerem smo sedaj. to nam pride prav, ko iz cilja lahko
                    // enolicno dolocimo pot iz katere smo prisli
                    pred[povezave.get(u).get(i)] = u;
                    queue.add(povezave.get(u).get(i));

                    // ce pa naletimo na konc odpremo sampanjec in smo lahko veseli
                    if (povezave.get(u).get(i) == konc)
                        return true;
                }
            }
        }
        System.out.println("CE SMO TU JE NEKI NAROBE");
        // nej nebi prsli sm cene je neki nrobe
        return false;
    }