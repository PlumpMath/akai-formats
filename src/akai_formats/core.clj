(ns akai-formats.core
  (:require [clojurewerkz.buffy.core :refer [compose decompose compose-buffer spec int32-type string-type boolean-type]]
            [clojure.java.io :refer [file output-stream input-stream]] )
  (:import [java.nio ByteBuffer]))


(def sample-path "resources/example_files/DK4CH.snd")

(def sample-spec
  (spec
    :ni1 (string-type 3)
    :sampling-rate (boolean-type)
    :midi-root-node (string-type 1)
    :filename (string-type 12)
    ))

(comment

  (with-open [in (input-stream (file sample-path))]
    (let [buf (byte-array 1000)
          ; n (.read in buf)
          f (decompose buf)]
      buf))
  )


; Length   Format      Description
; --------------------------------------------------------------
;    1                 3
;    1                 Not important: 0 for 22050Hz, 1 for 44100Hz
;    1     unsigned    MIDI root note (C3=60)
;   12     AKAII       Filename
;    1                 128
;    1     unsigned    Number of active loops
;    2                 0,0
;    1     unsigned    Loop mode: 0=in release 1=until release
;                                 2=none       3=play to end
;    1     signed      Cents tune -50...+50
;    1     signed      Semi tune  -50...+50
;    4                 0,8,2,0

;    4     unsigned    Number of sample words
;    4     unsigned    Start marker
;    4     unsigned    End marker

;    4     unsigned    Loop 1 marker
;    2     unsigned    Loop 1 fine length   (65536ths)
;    4     unsigned    Loop 1 coarse length (words)
;    2     unsigned    Loop 1 time          (msec. or 9999=infinite)

;   84     [as above]  Loops 2 to 8

;    4                 0,0,255,255
;    2     unsigned    Sampling frequency
;   10                 0,0,0...

